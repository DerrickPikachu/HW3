package com.example.hw3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Arithmetic {
    private String operation;
    private String[] numberToken;
    private String[] operatorToken;
    private HashMap<String, Integer> priority;

    private int operate(Stack<Integer> numberSt, Stack<String> operatorSt) {
        int num1, num2, res = 0;
        String operator = operatorSt.peek();
        operatorSt.pop();
        num1 = numberSt.peek();
        numberSt.pop();
        num2 = numberSt.peek();
        numberSt.pop();

        switch (operator) {
            case "+":
                res = num1 + num2;
                break;
            case "-":
                res = num2 - num1;
                break;
            case "*":
                res = num1 * num2;
                break;
            case "/":
                res = num2 / num1;
        }

        return res;
    }

    public Arithmetic(String o) {
        //initialize operation
        operation = o;
        operatorToken = new String[0];
        numberToken = new String[0];
        String[] initialNum = operation.split("\\+|-|\\*|/");
        String[] initialOp = operation.split("0|1|2|3|4|5|6|7|8|9");

        ArrayList<String> temNum = new ArrayList<>();
        for (String i : initialNum) {
            if (i.compareTo("") != 0)
                temNum.add(i);
        }

        ArrayList<String> temOp = new ArrayList<>();
        for (String s : initialOp) {
            if (s.compareTo("") != 0)
                temOp.add(s);
        }

        //handle the first number is negative
        if (operation.charAt(0) == '-') {
            temNum.set(0, "-" + temNum.get(0));
            temOp.remove(0);
        }

        numberToken = temNum.toArray(numberToken);
        operatorToken = temOp.toArray(operatorToken);

        //initialize priority
        priority = new HashMap<>();
        priority.put("+", new Integer(2));
        priority.put("-", new Integer(2));
        priority.put("*", new Integer(1));
        priority.put("/", new Integer(1));

//        for (String i : numberToken) {
//            System.out.println(i);
//        }
//        System.out.println();
//        for (String i : operatorToken) {
//            System.out.println(i);
//        }
    }

    public Arithmetic() {
        operation = "";
    }

    public void setString(String o) {
        operation = o;
    }

    public int getResult() {
        Stack<String> operatorSt = new Stack<>();
        Stack<Integer> numberSt = new Stack<>();


        if (Character.isDigit(operation.charAt(operation.length() - 1))) {
            //the last character is a number, not an operator
            if (numberToken.length == 1)
                return Integer.valueOf(numberToken[0]);
            else if (numberToken.length == 0)
                return 0;
        }
        else {
            //throw exception
        }

        //initialize
        numberSt.push(new Integer(numberToken[0]));
        numberSt.push(new Integer(numberToken[1]));
        operatorSt.push(operatorToken[0]);

        for (int i=2, o=1; i<numberToken.length && o<operatorToken.length; i++, o++) {
            String op = operatorToken[o];

            while (!operatorSt.empty() && priority.get(op) >= priority.get(operatorSt.peek())) {
                //operate previous result
                numberSt.push(operate(numberSt, operatorSt));
            }

            //push into stack
            numberSt.push(new Integer(numberToken[i]));
            operatorSt.push(op);
        }

        while (!operatorSt.empty())
            numberSt.push(operate(numberSt, operatorSt));

        return numberSt.peek();
    }
}
