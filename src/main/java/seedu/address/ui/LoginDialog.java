package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.AuthenticateCommand;
import seedu.address.logic.commands.exceptions.AuthenticateException;

/**
 * Controller for login dialog.
 */
public class LoginDialog extends UiPart<Stage> {
    private static final String FXML = "LoginDialog.fxml";
    private static final Logger logger = LogsCenter.getLogger(LoginDialog.class);
    private AuthenticateCommand authenticateCommand;
    private Logic logic;
    private ResultDisplay resultDisplay;
    private MainWindow mainWindow;


    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;

    public LoginDialog(Stage root) {
        super(FXML, root);
    }

    /**
     * Constructor to initialise LoginDialog from MainWindow
     *
     * @param logic LogicManager for MainWindow
     */
    public LoginDialog(Logic logic, MainWindow mainWindow) {
        this(new Stage());
        this.logic = logic;
        this.mainWindow = mainWindow;
    }

    @FXML
    /**
     * Passes the username and password to logic to authenticate
     */
    private void handleAuthenticateUser() {
        // passes login details to Auth
        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();

        if (username.equals("") || password.equals("")) {
            return;
        }

        usernameTextField.setText("");
        passwordPasswordField.setText("");

        try {
            authenticateCommand = new AuthenticateCommand(username, password);
            String message = authenticateCommand.authenticateUser(this.logic);

            resultDisplay.setFeedbackToUser(message);
            mainWindow.handleShowData();

            Stage stage = (Stage) usernameTextField.getScene().getWindow();
            stage.close();
        } catch (AuthenticateException e) {
            // show a login error message on commandBox
            resultDisplay.setFeedbackToUser(e.getMessage());

            Stage stage = (Stage) usernameTextField.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Shows the login dialog.
     * @throws IllegalStateException
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
        logger.fine("Showing login dialog.");
        getRoot().show();
        getRoot().centerOnScreen();
        this.usernameTextField.requestFocus();
    }

    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Returns true if the login dialog is currently being shown.
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
