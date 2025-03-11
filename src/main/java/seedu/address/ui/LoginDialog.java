package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for login dialog.
 */
public class LoginDialog extends UiPart<Stage> {

    private static final String FXML = "LoginDialog.fxml";
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public LoginDialog(Stage root) {
        super(FXML, root);
    }

    public LoginDialog() {
        this(new Stage());
    }

    public void authenticateUser() {
        // passes login details to Auth
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
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
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

}
