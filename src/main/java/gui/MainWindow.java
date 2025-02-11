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

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image tiffyImage = new Image(this.getClass().getResourceAsStream("/images/Tiffy.png"));

    private Deque<String> outputBuffer = new LinkedList<>();

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setTiffy(Tiffy t) {
        tiffy = t;
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        try {
            tiffy.handleRequests(input);
        } catch (Exception e) {
            UiManager.getInstance().printException(e);
        }

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, this.userImage)
        );
        flushBuffer();
        userInput.clear();
    }

    private void flushBuffer() {
        for (String output : outputBuffer) {
            dialogContainer.getChildren().addAll(
                    DialogBox.getTiffyDialog(output, this.tiffyImage)
            );
        }
        outputBuffer.clear();
    }

    public void setOutputMessage(String outputMessage) {
        this.outputBuffer.add(outputMessage);
    }
}
