package seedu.address.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.OrgID;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private final Map<OrgID, Boolean> expandedCells = new HashMap<>();

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using
     * a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {

        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                PersonCard personCard = new PersonCard(person, getIndex() + 1);
                preserveExpandedState(personCard, person.getOrgID());
                setGraphic(personCard.getRoot());

                // Listen for expansion changes and update map
                TitledPane titledPane = (TitledPane) personCard.getRoot().lookup("#cardPaneTitledPane");
                if (titledPane != null) {
                    titledPane.expandedProperty().addListener((obs, oldVal, newVal) -> {
                        expandedCells.put(person.getOrgID(), newVal);
                    });
                }

            }
        }

        private void preserveExpandedState(PersonCard newCard, OrgID id) {

            boolean isExpanded = expandedCells.getOrDefault(id, false);

            TitledPane newTitledPane = (TitledPane) newCard.getRoot().lookup("#cardPaneTitledPane");
            if (newTitledPane != null) {
                newTitledPane.setExpanded(isExpanded);
            }
        }
    }

}
