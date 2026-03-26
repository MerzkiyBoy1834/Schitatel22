package org.example;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UI {
    private Backend backend = new Backend();
    private TextField display = new TextField();
    
    private double currentValue = 0;
    private String lastOperation = "=";
    private boolean startNewNumber = true;

    public void start(Stage stage) {
        display.setEditable(false);
        display.setStyle("-fx-font-size: 24px; -fx-alignment: right;");

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        int row = 0, col = 0;
        for (String text : buttons) {
            Button btn = new Button(text);
            btn.setStyle("-fx-font-size: 18px; -fx-min-width: 60px; -fx-min-height: 60px;");
            btn.setOnAction(e -> onButtonClick(text));
            grid.add(btn, col, row);
            col++;
            if (col > 3) {
                col = 0;
                row++;
            }
        }

        Scene scene = new Scene(grid, 350, 450);
        stage.setTitle("Schitatel228 - Java calc");
        stage.setScene(scene);
        stage.show();
    }

    private void onButtonClick(String text) {
        if (text.matches("[0-9]")) {
            if (startNewNumber) {
                display.setText(text);
                startNewNumber = false;
            } else {
                display.setText(display.getText() + text);
            }
        } 
        else if (text.equals("C")) {
            display.setText("0");
            currentValue = 0;
            lastOperation = "=";
            startNewNumber = true;
        }
        else if (text.matches("[+\\-*/]") || text.equals("=")) {
            double currentNumber = Double.parseDouble(display.getText());
            
            currentValue = backend.calc(currentValue, currentNumber, lastOperation);
            display.setText(String.valueOf(currentValue));
            
            lastOperation = text;
            startNewNumber = true;
        }
    }
}
