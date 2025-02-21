package tiffy;

import exception.TiffyException;
import utility.RequestHandler;
import manager.UiManager;

public class Tiffy {
    public void handleRequests(String input) throws TiffyException {
        RequestHandler.getInstance().handleRequest(input);
        assert input != null && !input.isBlank() : "Input command cannot be null or empty";
    }

    public void initialize() {
        UiManager.getInstance().greetUser();
    }
}