package tiffy;

import exception.TiffyException;
import utility.RequestHandler;
import manager.UiManager;

/**
 * Main class for handling user interactions and requests in the Tiffy application.
 */
public class Tiffy {

    /**
     * Processes user input commands by delegating to the RequestHandler.
     *
     * @param input The user command to be processed.
     * @throws TiffyException If an error occurs while handling the request.
     * @throws AssertionError If the input is null or empty.
     */
    public void handleRequests(String input) throws TiffyException {
        RequestHandler.getInstance().handleRequest(input);
        assert input != null && !input.isBlank() : "Input command cannot be null or empty";
    }

    /**
     * Initializes the application by greeting the user via the UI manager.
     */
    public void initialize() {
        UiManager.getInstance().greetUser();
    }
}