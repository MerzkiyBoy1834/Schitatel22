package org.example;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        UI ui = new UI();
        ui.start(primaryStage);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
