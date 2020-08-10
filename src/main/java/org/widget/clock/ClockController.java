package org.widget.clock;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClockController implements Initializable {
    @FXML Label clockLabel;

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

    public void LabelOnClick(MouseEvent mouseEvent) {
        // Triple click for exit
        if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 3) {
            applicationExit();
        // Workaround for correct appearance of context menu
        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            ClockFX.getStage().requestFocus();
        }
    }

    public void settingsMenuAction() {
        // TODO: make settings
    }

    public void exitMenuAction() {
        applicationExit();
    }

    private void applicationExit() {
        Platform.exit();
        System.exit(0);
    }
}
