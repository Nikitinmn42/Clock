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
        Platform.setImplicitExit(true);
        // Schedule tasks for updating time
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss"); // for tests show seconds
        Runnable updateClock = () -> {
            try {
                Platform.runLater(() -> clockLabel.setText(LocalDateTime.now().format(formatter)));
            } catch (Exception e) {
                Logger.getGlobal().log(Level.SEVERE, "Failed to update time.", e);
            }
        };
        executorService.scheduleAtFixedRate(updateClock, 0, 300, TimeUnit.MILLISECONDS);
    }

    public void LabelOnClick(MouseEvent mouseEvent) {
        // Triple click for exit
        if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 3) {
            Platform.exit();
            System.exit(0);
        }
    }
}
