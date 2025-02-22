package gui;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tiffy.Tiffy;
import manager.UiManager;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private Tiffy tiffy;
    private boolean isErrorMessage = false;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/EpicFace.png"));
    private final Image tiffyImage = new Image(this.getClass().getResourceAsStream("/images/Tiffy.png"));

    private final Deque<String> outputBuffer = new LinkedList<>();

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setTiffy(Tiffy t) {
        this.tiffy = t;
    }

    @FXML
    private void handleUserInput() {
        String input = this.userInput.getText();
        try {
            this.tiffy.handleRequests(input);
        } catch (Exception e) {
            UiManager.getInstance().printException(e);
        }

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, this.userImage)
        );
        flushBuffer();
        this.userInput.clear();
    }

    public void flushBuffer() {
        StringBuilder finalOutput = new StringBuilder();
        for (String outputs : this.outputBuffer) {
            finalOutput.append(outputs);
        }
        dialogContainer.getChildren().addAll(
                this.isErrorMessage ?
                        DialogBox.getTiffyError(finalOutput.toString(), this.tiffyImage) :
                        DialogBox.getTiffyDialog(finalOutput.toString(), this.tiffyImage)
        );
        this.outputBuffer.clear();
        this.isErrorMessage = false;
    }

    public void setOutputMessage(String outputMessage) {
        this.outputBuffer.add(outputMessage);
    }

    public void toggleError(boolean isError) {
        this.isErrorMessage = isError;
    }
}
