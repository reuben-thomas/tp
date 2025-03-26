package seedu.address.ui;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CreateUserCommand;
import seedu.address.logic.commands.exceptions.CreateUserException;


public class RegisterDialog extends UiPart<Stage> {
    private static final String FXML = "RegisterDialog.fxml";
    private static final Logger logger = LogsCenter.getLogger(seedu.address.ui.RegisterDialog.class);
    private Logic logic;
    private ResultDisplay registerDisplay;
    private ResultDisplay resultDisplay;
    private CreateUserCommand createUserCommand;

    @javafx.fxml.FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private StackPane registerDisplayPlaceholder;

    public RegisterDialog(Stage root) {
        super(FXML, root);
    }

    /**
     * Constructor to initialise RegisterDialog from MainWindow
     *
     * @param logic LogicManager from MainWindow
     */
    public RegisterDialog(Logic logic) {
        this(new Stage());
        this.logic = logic;

        registerDisplay = new ResultDisplay();
        registerDisplayPlaceholder.getChildren().add(registerDisplay.getRoot());
    }

    /**
     * Passes the username and password to register to create new user.
     */
    public void registerUser() {
        logger.info("Attempting to create user");
        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();

        try {
            createUserCommand = new CreateUserCommand(username, password, logic);
            String message = createUserCommand.createNewUser();
            resultDisplay.setFeedbackToUser(message);
            Stage stage = (Stage) usernameTextField.getScene().getWindow();
            stage.close();
        } catch (CreateUserException e) {
            registerDisplay.setFeedbackToUser(e.getMessage());
        } catch (IOException e) {
            registerDisplay.setFeedbackToUser("Failed to save new account to accounts file");
        }
    }

    /**
     * Shows the register dialog.
     * @throws IllegalStateException <ul>
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing register dialog.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Returns true if the register dialog is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Receives a reference to resultDisplay
     *
     * @param resultDisplay Display to show result
     */
    public void setResultDisplay(ResultDisplay resultDisplay) {
        this.resultDisplay = resultDisplay;
    }

}
