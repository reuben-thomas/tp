package seedu.address.ui;

import java.util.Comparator;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Status;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final int DROPDOWN_WIDTH = 45;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    // Containers
    @FXML
    private HBox cardPane;
    @FXML
    private GridPane cardPaneHeader;
    @FXML
    private TitledPane cardPaneTitledPane;

    // Header Fields
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label orgId;
    @FXML
    private Label deviceInfo;
    @FXML
    private Label status;

    // Expanded Fields
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;

        // Setup cardPaneHeader to be responsive to the width of the personListPanel
        setupResponsiveCardPaneHeader();

        // Header Fields
        setupCopyableLabel(id, String.valueOf(displayedIndex));
        setupCopyableLabel(name, person.getName().fullName);
        setupCopyableLabel(orgId, person.getOrgID().value);
        setupCopyableLabel(deviceInfo, person.getDeviceInfo().toString(), UnicodeIcons.LAPTOP);
        setupCopyableStatusLabel(status, person.getStatus());

        // Expanded Fields
        setupCopyableLabel(phone, person.getPhone().value, UnicodeIcons.PHONE);
        setupCopyableLabel(email, person.getEmail().value, UnicodeIcons.EMAIL);
        setupCopyableLabel(address, person.getAddress().value, UnicodeIcons.HOME);

        // Tags
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    setupCopyableLabel(tagLabel, tag.tagName);
                    tags.getChildren().add(tagLabel);
                });
    }

    /**
     * Sets the label text with an icon.
     *
     * @param label The label to be set up.
     * @param text  The text to be displayed on the label.
     * @param icon  The icon to be displayed next to the text.
     */
    private static void setupCopyableLabel(Label label, String text, String icon) {
        label.setText(icon + "   " + text);
        PersonCard.setLabelCopyable(label, text);
    }

    /**
     * Sets the label text.
     *
     * @param label The label to be set up.
     * @param text  The text to be displayed on the label.
     */
    private static void setupCopyableLabel(Label label, String text) {
        label.setText(text);
        PersonCard.setLabelCopyable(label, text);
    }

    /**
     * Sets the status text of the person card, with the appropriate icon.
     *
     * @param status The status of the person.
     */
    private static void setupCopyableStatusLabel(Label label, Status status) {
        label.getStyleClass().add(PersonCard.getStatusStyleClass(status));

        switch (status) {
        case NONE:
            PersonCard.setupCopyableLabel(label, status.toDisplayString(), UnicodeIcons.CHECK);
            break;
        case PENDING_APPROVAL:
            PersonCard.setupCopyableLabel(label, status.toDisplayString(), UnicodeIcons.CLOCK);
            break;
        case SERVICING:
            PersonCard.setupCopyableLabel(label, status.toDisplayString(), UnicodeIcons.WRENCH_CLOCK);
            break;
        case PENDING_EXTERNAL:
            PersonCard.setupCopyableLabel(label, status.toDisplayString(), UnicodeIcons.BUSINESS_TIME);
            break;
        case ON_HOLD:
            PersonCard.setupCopyableLabel(label, status.toDisplayString(), UnicodeIcons.COG_PAUSE);
            break;
        default:
            assert false : "Invalid status value";
        }
    }

    /**
     * Gets the style class for the status label.
     *
     * @param status The status of the person.
     */
    private static String getStatusStyleClass(Status status) {
        switch (status) {
        case NONE:
            return "status-none";
        case PENDING_APPROVAL:
            return "status-pending-approval";
        case SERVICING:
            return "status-servicing";
        case PENDING_EXTERNAL:
            return "status-pending-external";
        case ON_HOLD:
            return "status-on-hold";
        default:
            break;
        }

        throw new AssertionError("An invalid status value was provided, "
                + "or was not handled correctly.");
    }

    /**
     * Sets up a label to be copyable when right-clicked, and optionally formatted with an icon.
     *
     * @param label      The label to be set up.
     * @param copyString The string to be copied when the label is clicked.
     */
    private static void setLabelCopyable(Label label, String copyString) {
        // Blank labels used as spacers should not be copyable
        if (label.getText().isBlank()) {
            return;
        }

        // Tooltip and cursor to indicate that the label is copyable
        label.setTooltip(new Tooltip("Right-lick to copy"));
        label.setCursor(Cursor.HAND);

        // Copy the text to clipboard when clicked
        label.setOnMouseClicked(event -> {
            // Only copy on right-click
            if (!event.getButton().toString().equals("SECONDARY")) {
                return;
            }

            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(copyString);
            clipboard.setContent(content);
            event.consume();
        });
    }

    /**
     * Sets the width of the cardPaneHeader to be the same as the personListPanel.
     * <p>
     * This is a workaround that is done since the {@code graphics} within the JavaFX {@code TitledPane} does not
     * allow for an alternative method for the list to be set.
     */
    private void setupResponsiveCardPaneHeader() {
        cardPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            // The personListPanel is used as a reference width, since its maximum width is dependent on the window,
            // whereas all of its child components will expand and be scrollable horizontally, providing a delayed
            // width, leading to jitters in the UI.
            // The parent, child relationship referenced, in order are as follows:
            // cardPane (VBox) -> personListView (ListView) -> personListPanel (VBox)
            Node personListPanel = cardPane.getParent().getParent().getParent();
            double personListPanelWidth = cardPane.getParent().getParent().getParent().getLayoutBounds().getWidth();
            double idWidth = id.getLayoutBounds().getWidth();

            // This guarantees that the update is performed even if the application window or the node associated
            // with the listener is not active or not in focus.
            Platform.runLater(() -> {
                cardPaneHeader.setPrefWidth(personListPanelWidth - idWidth - DROPDOWN_WIDTH);
            });
        });
    }
}
