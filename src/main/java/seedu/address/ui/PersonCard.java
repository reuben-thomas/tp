package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
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
        id.setText(String.valueOf(displayedIndex));
        name.setText(person.getName().fullName);
        orgId.setText(person.getOrgID().value);
        deviceInfo.setText(formatWithIcon(person.getDeviceInfo().toString(), UnicodeIcons.LAPTOP));
        setStatusText(person.getStatus());
        setStatusStyleClass(person.getStatus());

        // Expanded Fields
        phone.setText(formatWithIcon(person.getPhone().value, UnicodeIcons.PHONE));
        email.setText(formatWithIcon(person.getEmail().value, UnicodeIcons.EMAIL));
        address.setText(formatWithIcon(person.getAddress().value, UnicodeIcons.HOME));
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

    }


    /**
     * Sets the width of the cardPaneHeader to be the same as the personListPanel.
     *
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
            cardPaneHeader.setPrefWidth(personListPanelWidth - idWidth - DROPDOWN_WIDTH);
        });
    }

    private void setStatusText(Status status) {
        switch (status) {
        case NONE:
            this.status.setText(formatWithIcon(status.toDisplayString(),
                    UnicodeIcons.CHECK));
            break;
        case PENDING_APPROVAL:
            this.status.setText(formatWithIcon(status.toDisplayString(),
                    UnicodeIcons.CLOCK));
            break;
        case SERVICING:
            this.status.setText(formatWithIcon(status.toDisplayString(),
                    UnicodeIcons.WRENCH_CLOCK));
            break;
        case PENDING_EXTERNAL:
            this.status.setText(formatWithIcon(status.toDisplayString(),
                    UnicodeIcons.BUSINESS_TIME));
            break;
        case ON_HOLD:
            this.status.setText(formatWithIcon(status.toDisplayString(),
                    UnicodeIcons.COG_PAUSE));
            break;
        default:
            assert false : "Invalid status value";
        }
    }

    private void setStatusStyleClass(Status status) {
        switch (status) {
        case NONE:
            this.status.getStyleClass().add("status-none");
            break;
        case PENDING_APPROVAL:
            this.status.getStyleClass().add("status-pending-approval");
            break;
        case SERVICING:
            this.status.getStyleClass().add("status-servicing");
            break;
        case PENDING_EXTERNAL:
            this.status.getStyleClass().add("status-pending-external");
            break;
        case ON_HOLD:
            this.status.getStyleClass().add("status-on-hold");
            break;
        default:
            assert false : "Invalid status value";
        }
    }

    private String formatWithIcon(String text, String icon) {
        return icon + "   " + text;
    }
}
