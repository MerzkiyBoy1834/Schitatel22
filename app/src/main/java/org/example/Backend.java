package org.example;

import java.util.ArrayList;
import java.util.List;

public class Backend {
    public double evaluateExpression(String expression) {
        List<Double> numbers = new ArrayList<>();
        List<Character> operators = new ArrayList<>();

        String cleaned = expression.replaceAll("\\s+", "");
        if (cleaned.isEmpty()) {
            return 0;
        }

        StringBuilder currentNumber = new StringBuilder();
        for (int i = 0; i < cleaned.length(); i++) {
            char ch = cleaned.charAt(i);
            if (Character.isDigit(ch) || ch == '.') {
                currentNumber.append(ch);
            } else if (isOperator(ch)) {
                if (currentNumber.isEmpty()) {
                    throw new IllegalArgumentException("Invalid expression");
                }
                numbers.add(Double.parseDouble(currentNumber.toString()));
                currentNumber.setLength(0);
                operators.add(ch);
            } else {
                throw new IllegalArgumentException("Invalid character in expression");
            }
        }

        if (currentNumber.isEmpty()) {
            throw new IllegalArgumentException("Invalid expression");
        }
        numbers.add(Double.parseDouble(currentNumber.toString()));

        for (int i = 0; i < operators.size(); ) {
            char op = operators.get(i);
            if (op == '*' || op == '/') {
                double left = numbers.get(i);
                double right = numbers.get(i + 1);
                if (op == '/' && right == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                double result = (op == '*') ? left * right : left / right;
                numbers.set(i, result);
                numbers.remove(i + 1);
                operators.remove(i);
            } else {
                i++;
            }
        }

        double result = numbers.get(0);
        for (int i = 0; i < operators.size(); i++) {
            char op = operators.get(i);
            double right = numbers.get(i + 1);
            result = (op == '+') ? result + right : result - right;
        }

        return result;
    }

    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }
}
