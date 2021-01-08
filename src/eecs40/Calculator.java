package eecs40;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;


public class Calculator implements CalculatorInterface {

    String currentDisplay="0";
    ArrayList<String> expression = new ArrayList<>();
    String displayHistory="";
    boolean hasDot = false;
    boolean hasError = false;
    Stack<String> operation = new Stack<>();
    Queue<String> number = new LinkedList<>();
    double num=0;
    double answer=0;
    boolean hasAns = false;

    public void acceptInput(String s){
        if(s.length()==1) {
            if (currentDisplay.equals("NaN") || hasError) {
                if (s.equals("c") || s.equals("C")) {
                    clearAll();
                }
            }
            else{
                if (s.equals(".")) {
                    if(displayHistory.endsWith("+")||displayHistory.endsWith("-")||
                       displayHistory.endsWith("*")||displayHistory.endsWith("/")){
                        currentDisplay = "Error";
                        hasError = true;
                    } else if (!hasDot) {
                        currentDisplay = currentDisplay + s;
                        displayHistory = displayHistory + s;
                        hasDot = true;
                    } else if (!currentDisplay.endsWith(".")) {
                        hasError = true;
                        currentDisplay = "Error";
                    }

                } else if (s.equals("0") || s.equals("1") || s.equals("2")
                        || s.equals("3") || s.equals("4") || s.equals("5")
                        || s.equals("6") || s.equals("7") || s.equals("8") || s.equals("9")) {
                    if (displayHistory.endsWith("-") && displayHistory.length() == 1) {
                        currentDisplay = "-" + s;
                        displayHistory = displayHistory + s;
                    } else if (currentDisplay.equals("0")) {
                        currentDisplay = s;
                        displayHistory = displayHistory + s;
                    } else if (displayHistory.endsWith("+") || displayHistory.endsWith("-") ||
                            displayHistory.endsWith("*") || displayHistory.endsWith("/")) {
                        expression.add(displayHistory.substring(displayHistory.length() - 1));
                        currentDisplay = s;
                        displayHistory = displayHistory + s;
                    } else {
                        if (hasAns) {
                            currentDisplay = s;
                            expression.clear();
                            hasAns = false;
                        } else {
                            currentDisplay = currentDisplay + s;
                        }
                        displayHistory = displayHistory + s;
                    }
                } else if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")) {
                    if (displayHistory.endsWith(".") || displayHistory.endsWith("+") ||
                            displayHistory.endsWith("-") || displayHistory.endsWith("*") || displayHistory.endsWith("/")) {
                        currentDisplay = "Error";
                        hasError = true;
                    } else if (displayHistory.equals("") && s.equals("-")) {
                        displayHistory = displayHistory + "-";
                    } else if (displayHistory.equals("") && currentDisplay.equals("0")) {
                        expression.add("0");
                        expression.add(s);
                        displayHistory = displayHistory + "0" + s;
                    } else if (hasAns) {
                        hasAns = false;
                        displayHistory = displayHistory + s;
                    } else {
                        expression.add(currentDisplay);
                        displayHistory = displayHistory + s;
                        hasDot = false;
                    }
                } else if (s.equals("=")) {
                    if (displayHistory.endsWith("+") || displayHistory.endsWith("-") ||
                            displayHistory.endsWith("*") || displayHistory.endsWith("/")) {
                        currentDisplay = "Error";
                        hasError = true;
                    } else if (expression.size() == 1 || expression.size() == 0) {
                        currentDisplay = "" + currentDisplay;
                    } else if (!displayHistory.equals("")) {
                        expression.add(currentDisplay);
                        displayHistory = displayHistory + s;
                        organize();
                        calculate();
                    }

                } else if (s.equals("c") || s.equals("C")) {
                    clearAll();
                }
            }
        }
        else{
            System.out.println("You are only allow to press one button at a time!");
        }
    }

    public String getDisplayString(){
        return currentDisplay;
    }

    public void organize(){
        for(String symbol: expression){

            if(symbol.equals("+") || symbol.equals("-")){
                if(operation.empty()){
                    operation.push(symbol);
                }
                else if(operation.peek().equals("+")||operation.peek().equals("-")||
                        operation.peek().equals("*")||operation.peek().equals("/")){
                    number.add(operation.lastElement());
                    operation.pop();
                    operation.push(symbol);
                }
            }
            else if(symbol.equals("*")||symbol.equals("/")){
                if(operation.empty()||operation.peek().equals("+")||operation.peek().equals("-")){
                    operation.push(symbol);
                }
                else if(operation.peek().equals("*")||operation.peek().equals("/")){
                    number.add(operation.lastElement());
                    operation.pop();
                    operation.push(symbol);
                }
            }
            else{
                number.add(symbol);
            }
        }
        while(!operation.isEmpty()){
            number.add(operation.lastElement());
            operation.pop();
        }
    }

    public void calculate(){
        for(String element:number){
            try{
                num=Double.parseDouble(element);
                operation.push(element);
            }catch(Exception e){
                switch (element) {
                    case "+":
                        answer = Double.parseDouble(operation.get(operation.size() - 2)) + Double.parseDouble(operation.lastElement());
                        operation.pop();
                        operation.pop();
                        operation.push(String.valueOf(answer));
                        break;
                    case "-":
                        answer = Double.parseDouble(operation.get(operation.size() - 2)) - Double.parseDouble(operation.lastElement());
                        operation.pop();
                        operation.pop();
                        operation.push(String.valueOf(answer));
                        break;
                    case "*":
                        answer = Double.parseDouble(operation.get(operation.size() - 2)) * Double.parseDouble(operation.lastElement());
                        operation.pop();
                        operation.pop();
                        operation.push(String.valueOf(answer));
                        break;
                    case "/":
                        if(Double.parseDouble(operation.lastElement())==0){
                            currentDisplay = "NaN";
                        }
                        else {
                            answer = Double.parseDouble(operation.get(operation.size() - 2)) / Double.parseDouble(operation.lastElement());
                            operation.pop();
                            operation.pop();
                            operation.push(String.valueOf(answer));
                        }
                        break;
                }
            }
        }
        if(operation.size()!=1) {
            switch(operation.peek()){
                case "+":
                    answer = Double.parseDouble(operation.get(operation.size() - 3)) + Double.parseDouble(operation.get(operation.size() - 2));
                    operation.pop();
                    operation.pop();
                    operation.pop();
                    operation.push(String.valueOf(answer));
                    break;
                case "-":
                    answer = Double.parseDouble(operation.get(operation.size() - 3)) - Double.parseDouble(operation.get(operation.size() - 2));
                    operation.pop();
                    operation.pop();
                    operation.pop();
                    operation.push(String.valueOf(answer));
                    break;
                case "*":
                    answer = Double.parseDouble(operation.get(operation.size() - 3)) * Double.parseDouble(operation.get(operation.size() - 2));
                    operation.pop();
                    operation.pop();
                    operation.pop();
                    operation.push(String.valueOf(answer));
                    break;
                case "/":
                    if (Double.parseDouble(operation.get(operation.size() - 2)) == 0) {
                        currentDisplay = "NaN";
                    } else {
                        answer = Double.parseDouble(operation.get(operation.size() - 3)) / Double.parseDouble(operation.get(operation.size() - 2));
                        operation.pop();
                        operation.pop();
                        operation.pop();
                        operation.push(String.valueOf(answer));
                    }
                    break;
            }
        }
        expression.clear();
        number.clear();
        operation.clear();
        if(!currentDisplay.equals("NaN")) {
            currentDisplay = "" + answer;
            displayHistory = displayHistory + currentDisplay;
            expression.add(currentDisplay);
            hasAns = true;
        }
    }

    public void clearAll(){
        displayHistory = "";
        currentDisplay = "0";
        expression.clear();
        hasDot = false;
        hasError = false;
        number.clear();
        operation.clear();
        num=0;
        answer=0;
        hasAns=false;
    }
}
