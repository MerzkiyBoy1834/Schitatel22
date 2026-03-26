package org.example;

public class backend {
    // хорошая логика для калькулька что всё робит пуперски
    public float calc(float first, float second, char ch){
        float res = first;
        switch (ch) {
            case '+':
                 res += second;
                 break;
             case '-':
                 res -= second;
                 break;
             case '*':
                 res *= second;
                 break;
             case '/':
                try {
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
