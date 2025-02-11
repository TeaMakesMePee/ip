package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import tiffy.Tiffy;
import manager.UiManager;


public class Main extends Application {
    private Tiffy tiffy = new Tiffy();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setTiffy(tiffy);
            UiManager.getInstance().setMainWindow(fxmlLoader.getController());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}