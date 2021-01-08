package eecs40;

import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;

public class ExprCalculator implements CalculatorInterface {
    private String expression = "";
    private boolean hasError = false;
    private String errorMessage;

    private void eval(){
        int decision = legality(expression);
        if( decision == 0){
            ArrayList<String> firstList = categorize(expression);
            Queue<String> secondList = organize(firstList);
            String result = calculate(secondList);
            if(result.equals("NaN")){
                errorMessage = result;
            } else{
                expression = result;
            }
        } else if(decision == -1){
            errorMessage = "Error: Parentheses";
        } else{
            errorMessage = "Error";
        }
    }

    @Override
    public void acceptInput(String s){
        if (s.equalsIgnoreCase("=")) {
            eval();
        } else if (s.equalsIgnoreCase("Backspace")) {
            expression = expression.substring(0, expression.length() - 1);
        } else if (s.equalsIgnoreCase("C")) {
            expression = ""; // clear!
        } else { // accumulate input String
            expression = expression + s;
        }
    }

    @Override
    public String getDisplayString(){
        if(hasError){
            hasError = false;
            return errorMessage;
        }
        return expression;
    }

    private boolean isNum(String literal){
        try{
           Double.parseDouble(literal);
           return true;
        } catch(Exception e){
            return false;
        }
    }

    private double factorial(double num){
        double result = -1.0;
        if(num >= 0 && num <=1){
            result = 1.0;
        } else if(num > 1){
            result = num * factorial(num - 1);
        }
        return result;
    }

    private String calculate(Queue<String> list){
        double temp;
        Stack<String> container = new Stack<>();
        for(String ele: list){
            if(isNum(ele)){
                container.push(ele);
            } else if(ele.equals("sin")){
                temp = Math.sin(Double.parseDouble(container.peek()));
                if(Double.isInfinite(temp) || Double.isNaN(temp)){
                    hasError = true;
                    return "NaN";
                }
                container.pop();
                container.push(String.valueOf(temp));
            } else if(ele.equals("cos")){
                temp = Math.cos(Double.parseDouble(container.peek()));
                if(Double.isInfinite(temp) || Double.isNaN(temp)){
                    hasError = true;
                    return "NaN";
                }
                container.pop();
                container.push(String.valueOf(temp));
            } else if(ele.equals("tan")){
                temp = Math.tan(Double.parseDouble(container.peek()));
                if(Double.isInfinite(temp) || Double.isNaN(temp)){
                    hasError = true;
                    return "NaN";
                }
                container.pop();
                container.push(String.valueOf(temp));
            } else if(ele.equals("log")){
                temp = Math.log10(Double.parseDouble(container.peek()));
                if(Double.isInfinite(temp) || Double.isNaN(temp)){
                    hasError = true;
                    return "NaN";
                }
                container.pop();
                container.push(String.valueOf(temp));
            } else if(ele.equals("ln")){
                temp = Math.log(Double.parseDouble(container.peek()));
                if(Double.isInfinite(temp) || Double.isNaN(temp)){
                    hasError = true;
                    return "NaN";
                }
                container.pop();
                container.push(String.valueOf(temp));
            } else if(ele.equals("sqrt")){
                temp = Math.sqrt(Double.parseDouble(container.peek()));
                if(Double.isNaN(temp) || Double.isInfinite(temp)) {
                    hasError = true;
                    return "NaN";
                }
                container.pop();
                container.push(String.valueOf(temp));
            } else if(ele.equals("fac")){
                try {
                    temp = factorial(Double.parseDouble(container.peek()));
                } catch(ArithmeticException e){
                    hasError = true;
                    return "NaN";
                }
                container.pop();
                container.push(String.valueOf(temp));
            } else if(ele.equals("+")){
                temp = Double.parseDouble(container.elementAt(container.size()-2)) + Double.parseDouble(container.lastElement());
                container.pop();
                container.pop();
                container.push(String.valueOf(temp));
            } else if(ele.equals("-")){
                temp = Double.parseDouble(container.elementAt(container.size()-2)) - Double.parseDouble(container.lastElement());
                container.pop();
                container.pop();
                container.push(String.valueOf(temp));
            } else if(ele.equals("*")){
                temp = Double.parseDouble(container.elementAt(container.size()-2)) * Double.parseDouble(container.lastElement());
                container.pop();
                container.pop();
                container.push(String.valueOf(temp));
            } else if(ele.equals("/")){
                temp = Double.parseDouble(container.elementAt(container.size()-2)) / Double.parseDouble(container.lastElement());
                if(Double.isInfinite(temp) || Double.isNaN(temp)){
                    hasError = true;
                    return "NaN";
                }
                container.pop();
                container.pop();
                container.push(String.valueOf(temp));
            } else if(ele.equals("^")){
                temp = Math.pow(Double.parseDouble(container.elementAt(container.size()-2)),Double.parseDouble(container.lastElement()));
                container.pop();
                container.pop();
                container.push(String.valueOf(temp));
            } else if(ele.equals("mod")){
                temp = Double.parseDouble(container.elementAt(container.size()-2)) % Double.parseDouble(container.lastElement());
                if(Double.isInfinite(temp) || Double.isNaN(temp)) {
                    hasError = true;
                    return "NaN";
                }
                container.pop();
                container.pop();
                container.push(String.valueOf(temp));
            }
        }
        double result = Double.parseDouble(container.peek());
        String finalAns;
        if(result % 1.0 != 0){
            finalAns = String.format("%s",result);
        } else{
            finalAns = String.format("%.0f",result);
        }
        return finalAns;
    }

    private Queue<String> organize(ArrayList<String> expression){
        Queue<String> list = new LinkedList<>();
        Stack<String> storage = new Stack<>();
        for(String ele: expression){
            if(ele.equals("sin") || ele.equals("cos") || ele.equals("tan") || ele.equals("log") || ele.equals("ln") ||
                    ele.equals("sqrt") || ele.equals("fac")){
                if(storage.isEmpty()){
                    storage.push(ele);
                } else if(storage.peek().equals("sin") || storage.peek().equals("cos") || storage.peek().equals("tan") ||
                        storage.peek().equals("log") || storage.peek().equals("ln") || storage.peek().equals("sqrt") || storage.peek().equals("fac")){
                    list.add(storage.peek());
                    storage.pop();
                    storage.push(ele);
                } else{
                    storage.push(ele);
                }
            } else if(ele.equals("*") || ele.equals("/") || ele.equals("^") || ele.equals("mod")){
                if(storage.isEmpty()){
                    storage.push(ele);
                } else if(storage.peek().equals("sin") || storage.peek().equals("cos") || storage.peek().equals("tan") || storage.peek().equals("log") ||
                        storage.peek().equals("ln") || storage.peek().equals("sqrt") || storage.peek().equals("fac") ||
                        storage.peek().equals("*") || storage.peek().equals("/") || storage.peek().equals("^") || storage.peek().equals("mod")){
                    list.add(storage.peek());
                    storage.pop();
                    storage.push(ele);
                } else{
                    storage.push(ele);
                }
            } else if(ele.equals("+") || ele.equals("-")){
                if(storage.isEmpty()){
                    storage.push(ele);
                } else if(storage.peek().equals("sin") || storage.peek().equals("cos") || storage.peek().equals("tan") || storage.peek().equals("log") ||
                        storage.peek().equals("ln") || storage.peek().equals("sqrt") || storage.peek().equals("fac") || storage.peek().equals("*") ||
                        storage.peek().equals("/") || storage.peek().equals("^") || storage.peek().equals("mod") || storage.peek().equals("+") || storage.peek().equals("-")){
                    list.add(storage.peek());
                    storage.pop();
                    storage.push(ele);
                } else{
                    storage.push(ele);
                }
            } else if(ele.equals(")")){
                while(!storage.peek().equals("(")){
                    list.add(storage.peek());
                    storage.pop();
                }
                storage.pop();
            } else if(ele.equals("(")){
                storage.push(ele);
            } else{
                list.add(ele);
            }
        }
        while(!storage.isEmpty()){
            list.add(storage.peek());
            storage.pop();
        }
        return list;
    }

    private ArrayList<String> categorize(String input){
        expression = input.replaceAll("\\s","");
        ArrayList<String> myList = new ArrayList<>();
        int lastIndex = 0;
        for(int i = 0; i<expression.length(); i++){
            if(i<lastIndex){
                continue;
            }
            if(expression.charAt(i) == '+' || expression.charAt(i) == '-' || expression.charAt(i) == '*' ||
                    expression.charAt(i) == '/' || expression.charAt(i) == '^' || expression.charAt(i) == '(' ||
                    expression.charAt(i) == ')'){
                if(i == 0 && expression.charAt(i) == '-'){
                    continue;
                }
                if(i != 0 && Character.isDigit(expression.charAt(i-1))) {
                    myList.add(expression.substring(lastIndex, i));
                }
                myList.add(String.valueOf(expression.charAt(i)));
                lastIndex = i+1;
            } else if(expression.startsWith("mod",i)){
                myList.add(expression.substring(lastIndex,i));
                myList.add("mod");
                lastIndex = i+3;
            } else if(expression.startsWith("sin(",i) || expression.startsWith("cos(",i) ||
                    expression.startsWith("tan(",i) || expression.startsWith("log(",i) || expression.startsWith("fac(",i)){
                if(i != 0 && Character.isDigit(expression.charAt(i-1))){
                    myList.add(expression.substring(lastIndex,i));
                }
                myList.add(expression.substring(i,i+3));
                myList.add("(");
                lastIndex = i+4;
            } else if(expression.startsWith("ln(",i)){
                if(i != 0 && Character.isDigit(expression.charAt(i-1))){
                    myList.add(expression.substring(lastIndex,i));
                }
                myList.add(expression.substring(i,i+2));
                myList.add("(");
                lastIndex = i+3;
            } else if(expression.startsWith("sqrt(",i)){
                if(i != 0 && Character.isDigit(expression.charAt(i-1))){
                    myList.add(expression.substring(lastIndex,i));
                }
                myList.add(expression.substring(i,i+4));
                myList.add("(");
                lastIndex = i+5;
            } else if(i == expression.length()-1 && Character.isDigit(expression.charAt(i))){
                myList.add(expression.substring(lastIndex));
            }
        }
        return myList;
    }

    private int legality(String input){
        expression = input.replaceAll("\\s","");
        if(!parenthesesMismatch(expression)){
            hasError = true;
            return -1;
        }
        if(!checkParentheses(expression)){
            hasError = true;
            return -2;
        }
        if(!checkDot(expression)){
            hasError = true;
            return -3;
        }
        if(!checkNum(expression)){
            hasError = true;
            return -4;
        }
        if(!checkOperation(expression)){
            hasError = true;
            return -5;
        }
        hasError = false;
        return 0;
    }

    private boolean checkDot(String expression){
        if(expression.endsWith(".")){
            return false;
        }
        for(int i = 0; i<expression.length(); i++){
            if(expression.charAt(i) == '.'){
                if(expression.charAt(i+1) != '0' && expression.charAt(i+1) != '1' && expression.charAt(i+1) != '2' &&
                        expression.charAt(i+1) != '3' && expression.charAt(i+1) != '4' && expression.charAt(i+1) != '5' &&
                        expression.charAt(i+1) != '6' && expression.charAt(i+1) != '7' && expression.charAt(i+1) != '8' &&
                        expression.charAt(i+1) != '9'){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean parenthesesMismatch(String expression){
        Stack<Character> check = new Stack<>();
        for(int i = 0; i<expression.length(); i++){
            if(expression.charAt(i) == '('){
                check.push('(');
            } else if(expression.charAt(i) == ')'){
                if(check.isEmpty() || check.peek() != '('){
                    return false;
                }
                check.pop();
            }
        }
        return check.isEmpty();
    }

    private boolean checkOperation(String expression){
        if(expression.endsWith("+") || expression.endsWith("-") || expression.endsWith("*") ||
                expression.endsWith("/") || expression.endsWith("^") || expression.endsWith("mod")){
            return false;
        }
        for(int i = 0; i<expression.length(); i++){
            if(expression.charAt(i) == '+' || expression.charAt(i) == '-' || expression.charAt(i) == '*' ||
                    expression.charAt(i) == '/' || expression.charAt(i) == '^' || expression.startsWith("mod",i)){
               if(expression.charAt(i+1) == '+' || expression.charAt(i+1) == '-' || expression.charAt(i+1) == '*' ||
                       expression.charAt(i+1) == '/' || expression.charAt(i+1) == '^' || expression.charAt(i+1) == ')' ||
                       expression.startsWith("mod",i+1)){
                   return false;
               }
            }
        }
        return true;
    }

    private boolean checkParentheses(String expression){
        for(int i = 0; i<expression.length(); i++){
            if(expression.charAt(i) == '('){
                if(expression.charAt(i+1) == '+' || expression.charAt(i+1) == '-' || expression.charAt(i+1) == '*' ||
                        expression.charAt(i+1) == '/' || expression.charAt(i+1) == '^' || expression.charAt(i+1) == ')' ||
                        expression.startsWith("mod",i+1)){
                    return false;
                }
            } else if(expression.charAt(i) == ')'){
                if(i == expression.length()-1){
                    return true;
                }
                if(expression.charAt(i+1) != '+' && expression.charAt(i+1) != '-' && expression.charAt(i+1) != '*' &&
                        expression.charAt(i+1) != '/' && expression.charAt(i+1) != '^' && expression.startsWith("mod",i+1)){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkNum(String expression){
        for(int i = 0; i<expression.length(); i++){
            if(expression.charAt(i) == '0' || expression.charAt(i) == '1' || expression.charAt(i) == '2' ||
                    expression.charAt(i) == '3' || expression.charAt(i) == '4' || expression.charAt(i) == '5' ||
                    expression.charAt(i) == '6' || expression.charAt(i) == '7' || expression.charAt(i) == '8' || expression.charAt(i) == '9'){
                if(i == expression.length()-1){
                    return true;
                } else if(expression.charAt(i+1) == '(' || expression.startsWith("sin(",i+1) ||
                        expression.startsWith("cos(",i+1) || expression.startsWith("tan(",i+1) ||
                        expression.startsWith("log(",i+1) || expression.startsWith("ln(",i+1) ||
                        expression.startsWith("sqrt(",i+1) || expression.startsWith("fac(",i+1)){
                    return false;
                }
            }
        }
        return true;
    }
}
