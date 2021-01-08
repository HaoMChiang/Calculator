import eecs40.*;

import javax.swing.*;

public class CalculatorTestDrive {

    public static void main(String[] args) {
//        CalculatorForm cf = new CalculatorForm();
//        JFrame frame = new JFrame("Calculator");
//        frame.setContentPane(cf.panel1);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);

        CalculatorInterface calc = new ExprCalculator();

        calc.acceptInput("(457+992)/2+12*cos(0)");

        System.out.println(calc.getDisplayString()); // show (457+992)/2+12*cos(0)

        calc.acceptInput("=");

        System.out.println("Display: " + calc.getDisplayString()); // show 736.5

        calc.acceptInput("+(876-8*9");

        System.out.println("Display: " + calc.getDisplayString()); // show 736.5+(876-8*9

        calc.acceptInput("=");

        System.out.println("Display: " + calc.getDisplayString()); // show Error:Parentheses

        calc.acceptInput(")");

        System.out.println("Display: " + calc.getDisplayString()); // show 736.5+(876-8*9)

        calc.acceptInput("=");

        System.out.println("Display: " + calc.getDisplayString()); // show 1540.5




    }
}
