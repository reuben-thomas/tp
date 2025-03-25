package seedu.address.ui;

import org.fxmisc.richtext.CodeArea;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    @FXML
    private CodeArea commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;

        // Listen and consume only the enter key, other keys are handled by the CodeArea
        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleCommandEntered();
                event.consume(); // Prevents new line
            }
        });

        // Compute syntax highlighting when command text changed
        commandTextField.textProperty().addListener((observable, oldValue, newValue) ->
                commandTextField.setStyleSpans(0,
                        CommandSyntaxHighlighter.computeSyntaxHighlighting(commandTextField.getText())));
    }

    /**
     * Handles the Enter button pressed event.
     */
    private void handleCommandEntered() {
        String commandText = commandTextField.getText().trim();
        if (commandText.equals("")) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            commandTextField.clear();
        } catch (CommandException | ParseException e) {
            commandTextField.setStyleSpans(0, CommandSyntaxHighlighter.getFailureStyleSpan(commandTextField.getText()));
        }
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }
}

