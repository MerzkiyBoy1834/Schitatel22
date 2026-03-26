package org.example;

public class Backend {
    // хорошая логика для калькулька что всё робит пуперски
    public double calc(double first, double second, String ch){
        double res = first;
        switch (ch) {
            case "+":
                 res += second;
                 break;
             case "-":
                 res -= second;
                 break;
             case "*":
                 res *= second;
                 break;
             case "/":
                try {              // я буду на это дрочить до конца жизни нахуй, это просто ахуенно
                    res /= second;
                } catch (ArithmeticException e) {
                    System.out.println("You can`t divide by 0");
                   System.out.println(e);
                }
                break;
            default:
                System.out.println("Какой долбоеб не то жмет");
                break;
         }

         return res;
    }
}
