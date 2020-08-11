package org.widget.clock;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.*;

public class ClockFX extends Application {
    private static Window baseStage;
    private static Window clockWindow;

    @Override
    public void start(Stage primaryStage) throws Exception{
        baseStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Clock.fxml"));
        primaryStage.initStyle(StageStyle.UTILITY); // UTILITY stage doesn't show TaskBar icon
        primaryStage.setOpacity(0); // This stage is invisible
        primaryStage.setMaxHeight(1);
        primaryStage.setMaxWidth(1);
        primaryStage.show();
        // To make window without task bar icon, title and borders use Popup
        Popup clockPopup = new Popup();
        clockWindow = clockPopup;
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

    public static Window getBaseStage() {
        return baseStage;
    }

    public static Window getClockWindow() {
        return clockWindow;
    }
}
