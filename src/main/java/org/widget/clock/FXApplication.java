package org.widget.clock;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.*;

public class FXApplication extends Application {
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Clock.fxml"));
        primaryStage.initStyle(StageStyle.UTILITY); // UTILITY stage doesn't show TaskBar icon
        primaryStage.setOpacity(0); // This stage is invisible
        primaryStage.show();
        primaryStage.setHeight(0);
        primaryStage.setWidth(0);
        primaryStage.setIconified(true);
        // To make window without task bar icon, title and borders use Popup
        Popup clockPopup = new Popup();
        clockPopup.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_TOP_LEFT);
        clockPopup.getContent().add(root);
        clockPopup.show(primaryStage);
        clockPopup.setAnchorX(1380);
        clockPopup.setAnchorY(825);
        clockPopup.setOnHidden(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
