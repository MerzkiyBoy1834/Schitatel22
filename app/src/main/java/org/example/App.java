package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        Button btn1 = new Button("йобни менi");
        btn1.setOnAction(e -> System.out.println("ай, бiльно у ноге"));

        StackPane root = new StackPane();
        root.getChildren().add(btn1);

        Scene scene = new Scene(root, 400, 200);

        primaryStage.setTitle("APP - Aхуеть Потужно Похуй");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
