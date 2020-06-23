package org.widget.clock;

// Main class runs JavaFX application to workaround that the Java 11+ runtime will check if
// the main class extends javafx.application.Application, and if that is the case, it strongly requires
// the javafx platform to be available as a module, and not as a jar for example.
// It was discussed here: http://mail.openjdk.java.net/pipermail/openjfx-dev/2018-June/021977.html
public class Main {
    public static void main(String[] args) {
        FXApplication.main(args);
    }
}
