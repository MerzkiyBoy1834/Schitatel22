package org.example;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UI {
    private final Backend backend = new Backend();
    private final TextField expressionDisplay = new TextField();
    private final TextField resultDisplay = new TextField();
    private final StringBuilder expression = new StringBuilder();

    public void start(Stage stage) {
        try {
            Image icon = new Image(getClass().getResourceAsStream("/images/icon.png"));
            stage.getIcons().add(icon);
        } catch (Exception e) {
            System.out.println("Icon not found");
        }

        expressionDisplay.setEditable(false);
        expressionDisplay.setFocusTraversable(false);
        expressionDisplay.setStyle("-fx-font-size: 20px; -fx-alignment: right;");
        expressionDisplay.setText("0");

        resultDisplay.setEditable(false);
        resultDisplay.setFocusTraversable(false);
        resultDisplay.setStyle("-fx-font-size: 28px; -fx-alignment: right;");
        resultDisplay.setText("0");

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setStyle("-fx-padding: 10px;");

        for (int i = 0; i < 4; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(25);
            column.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(column);
        }
        for (int i = 0; i < 5; i++) {
            RowConstraints rowConstraint = new RowConstraints();
            rowConstraint.setPercentHeight(20);
            rowConstraint.setVgrow(Priority.ALWAYS);
            grid.getRowConstraints().add(rowConstraint);
        }
 
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "C", "+",
            "="
        };

        int row = 0, col = 0;
        for (String text : buttons) {
            Button btn = new Button(text);
            btn.setStyle("-fx-font-size: 18px;");
            btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            btn.setFocusTraversable(false);
            btn.setOnAction(e -> onButtonClick(text));
            if ("=".equals(text)) {
                grid.add(btn, 0, row, 4, 1);
            } else {
                grid.add(btn, col, row);
            }
            col++;
            if (col > 3) {
                col = 0;
                row++;
            }
        }

        VBox root = new VBox(8, expressionDisplay, resultDisplay, grid);
        root.setStyle("-fx-padding: 10px;");
        VBox.setVgrow(grid, Priority.ALWAYS);

        Scene scene = new Scene(root, 360, 600);
        attachKeyboardHandlers(scene);
        stage.setTitle("Schitatel228 - Java calc");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void attachKeyboardHandlers(Scene scene) {
        scene.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                onButtonClick("=");
                event.consume();
            }
        });

        scene.setOnKeyTyped(event -> {
            String input = event.getCharacter();
            if (input == null || input.isEmpty()) {
                return;
            }
            char ch = input.charAt(0);
            if (Character.isDigit(ch) || ch == '.' || ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                onButtonClick(String.valueOf(ch));
            } else if (ch == '=' || ch == '\n') {
                onButtonClick("=");
            }
        });

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.BACK_SPACE) {
                removeLastSymbol();
            } else if (code == KeyCode.DELETE || code == KeyCode.ESCAPE) {
                onButtonClick("C");
            }
        });
    }

    private void onButtonClick(String text) {
        if (text.matches("[0-9]") || text.equals(".")) {
            if (expression.length() == 0 && text.equals(".")) {
                expression.append('0');
            }
            if (expressionDisplay.getText().equals("0") && expression.length() == 0) {
                expressionDisplay.clear();
            }
            expression.append(text);
            expressionDisplay.setText(expression.toString());
        } else if (text.equals("C")) {
            expression.setLength(0);
            expressionDisplay.setText("0");
            resultDisplay.setText("0");
        } else if (text.matches("[+\\-*/]")) {
            if (expression.length() == 0 || endsWithOperator(expression)) {
                return;
            }
            expression.append(text);
            expressionDisplay.setText(expression.toString());
        } else if (text.equals("=")) {
            if (expression.length() == 0 || endsWithOperator(expression)) {
                return;
            }
            try {
                double result = backend.evaluateExpression(expression.toString());
                resultDisplay.setText(formatResult(result));
            } catch (Exception e) {
                resultDisplay.setText("Error");
            }
        }
    }

    private void removeLastSymbol() {
        if (expression.length() == 0) {
            return;
        }
        expression.deleteCharAt(expression.length() - 1);
        if (expression.length() == 0) {
            expressionDisplay.setText("0");
            resultDisplay.setText("0");
        } else {
            expressionDisplay.setText(expression.toString());
        }
    }

    private boolean endsWithOperator(StringBuilder value) {
        if (value.length() == 0) {
            return false;
        }
        char last = value.charAt(value.length() - 1);
        return last == '+' || last == '-' || last == '*' || last == '/';
    }

    private String formatResult(double value) {
        if (value == (long) value) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }
}
