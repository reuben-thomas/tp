package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

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
                preserveExpandedState(personCard);
                setGraphic(personCard.getRoot());
            }
        }

        private void preserveExpandedState(PersonCard newCard) {
            boolean isExpanded = false;
            if (getGraphic() instanceof Region) {
                TitledPane currentTitledPane = (TitledPane) getGraphic().lookup("#cardPaneTitledPane");
                if (currentTitledPane != null) {
                    isExpanded = currentTitledPane.isExpanded();
                }
            }

            TitledPane newTitledPane = (TitledPane) newCard.getRoot().lookup("#cardPaneTitledPane");
            if (newTitledPane != null) {
                newTitledPane.setExpanded(isExpanded);
            }
        }
    }

}
