package org.widget.clock;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClockController implements Initializable {
    @FXML private Label clockLabel;

    private ClockFX application;
    private Window baseStage;
    private Window clockWindow;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Schedule tasks for updating time
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        Runnable updateClock = () -> {
            try {
                Platform.runLater(() -> clockLabel.setText(LocalDateTime.now().format(formatter)));
            } catch (Exception e) {
                Logger.getGlobal().log(Level.SEVERE, "Failed to update time.", e);
            }
        };
        executorService.scheduleAtFixedRate(updateClock, 0, 1, TimeUnit.SECONDS);
    }

    public void labelOnClick(MouseEvent mouseEvent) {
        // Triple click for exit
        if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 3) {
            applicationExit();
        // Workaround for correct appearance of context menu
        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            baseStage.requestFocus();
        }
    }

    public void labelOnDragged(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            clockWindow.setX(event.getScreenX() + xOffset);
            clockWindow.setY(event.getScreenY() + yOffset);
            baseStage.setX(event.getScreenX() + xOffset);
            baseStage.setY(event.getScreenY() + yOffset);
        }
    }

    public void labelOnPressed(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            xOffset = clockWindow.getX() - event.getScreenX();
            yOffset = clockWindow.getY() - event.getScreenY();
        }
    }

    public void settingsMenuAction() {
        application.showSettings();
    }

    public void exitMenuAction() {
        applicationExit();
    }

    private void applicationExit() {
        Platform.exit();
        System.exit(0);
    }

    public void setApplication(ClockFX application) {
        this.application = application;
    }

    public void setBaseStage(Window baseStage) {
        this.baseStage = baseStage;
    }

    public void setClockWindow(Window clockWindow) {
        this.clockWindow = clockWindow;
    }
}
