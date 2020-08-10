package org.widget.clock;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.*;

public class ClockFX extends Application {
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Clock.fxml"));
        primaryStage.initStyle(StageStyle.UTILITY); // UTILITY stage doesn't show TaskBar icon
        primaryStage.setOpacity(0); // This stage is invisible
        primaryStage.setMaxHeight(1);
        primaryStage.setMaxWidth(1);
        primaryStage.show();
        // To make window without task bar icon, title and borders use Popup
        Popup clockPopup = new Popup();
        clockPopup.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_TOP_LEFT);
        clockPopup.getContent().add(root);
        clockPopup.show(primaryStage);
        clockPopup.setX(1380);
        clockPopup.setY(825);
        primaryStage.setX(1380);
        primaryStage.setY(825);
        clockPopup.setOnHidden(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return stage;
    }
}
