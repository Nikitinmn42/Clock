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
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Controller implements Initializable {
    @FXML Label clockLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.setImplicitExit(true);
        // Schedule tasks for updating time
        ScheduledThreadPoolExecutor timer = new ScheduledThreadPoolExecutor(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss"); // for tests show seconds
        ScheduledFuture<?> runningTimer = timer.scheduleAtFixedRate(
                () -> Platform.runLater(() -> clockLabel.setText(LocalDateTime.now().format(formatter))),
                0, 300, TimeUnit.MILLISECONDS);
    }

    public void LabelOnClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            if (mouseEvent.getClickCount() == 3) { // Triple click for exit
                Platform.exit();
                System.exit(0);
            } else if (mouseEvent.getClickCount() == 2) {
                System.out.println("double click - open settings"); // TODO: open settings
            }
        }
    }
}
