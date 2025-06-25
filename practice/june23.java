package practice;

import dataStructures.StackUsingLL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class june23 {

    public static void main (String[] args) {
        String exp1 = "(A+B) / (C*D)";
        String exp2 = "((A+B) / (C * D  )";
        String exp3 = "   ((A+B     )+ ( C  - D  ) +E )/ (F + G )   ";

        // Construct variable map to use for evaluating exp3 later
        // NOTE: exp3 with values plugged in evaluates to 7
        Map<Character, Integer> variableMap = new HashMap<>();
        variableMap.put('A', 100);
        variableMap.put('B', 27);
        variableMap.put('C', 52);
        variableMap.put('D', 19);
        variableMap.put('E', 34);
        variableMap.put('F', 11);
        variableMap.put('G', 15);

        // Make a string representation of infix exp3
        StringBuilder sb = new StringBuilder();
        String exp3WithValues = exp3.replaceAll("\\s", "");
        for (char c : exp3WithValues.toCharArray()) {
            if (Character.isLetter(c) && variableMap.containsKey(c)) {
                sb.append(variableMap.get(c));
            } else {
                sb.append(c);
            }
        }
        exp3WithValues = sb.toString();

        int decimal1 = 100; // binary 1100100
        int decimal2 = 1024; // binary 10000000000
        int decimal3 = 53; // binary 110101

        String binNum1 = "1110111"; // decimal 119
        String binNum2 = "1111010001"; // decimal 977

        System.out.println("exp1: " + exp1);
        System.out.println("exp1 is valid expression with parentheses: " + isValidParentheses(exp1));
        System.out.println("exp1 -> POSTFIX: " + infixToPostfix(exp1));
        System.out.println();
        System.out.println("exp2: " + exp2);
        System.out.println("exp2 is valid expression with parentheses: " + isValidParentheses(exp2));
        System.out.println();
        System.out.println("exp3: " + exp3);
        System.out.println("exp3 is valid expression with parentheses: " + isValidParentheses(exp3));
        System.out.println("exp3 -> POSTFIX: " + infixToPostfix(exp3));
        System.out.println("exp3, infix with variables plugged in: " + exp3WithValues);
        System.out.println("Evaluating exp3 using variableMap: " + evaluatePostfix(infixToPostfix(exp3), variableMap));
        System.out.println();

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("---Convert decimal to binary---");
        System.out.printf("original decimal number: %d | binary converted : %s\n", decimal1, convertDecimalToBinary(decimal1));
        System.out.printf("original decimal number: %d | binary converted : %s\n", decimal2, convertDecimalToBinary(decimal2));
        System.out.printf("original decimal number: %d | binary converted : %s\n", decimal3, convertDecimalToBinary(decimal3));
        System.out.println();

        System.out.println("---Convert binary to decimal---");
        System.out.printf("original binary number: %s | decimal converted : %s\n", binNum1, convertBinaryToDecimal(binNum1));
        System.out.printf("original binary number: %s | decimal converted : %s\n", binNum2, convertBinaryToDecimal(binNum2));

    }

    // Check if the math expression is valid with nesting of parentheses
    public static boolean isValidParentheses(String expression) {
        if (expression == null || expression.isEmpty()) {
            return false;
        }
        // Assuming that the expression only uses (), [], {}
        StackUsingLL<Character> stack = new StackUsingLL<>();
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');
        List<Character> openers = Arrays.asList('(', '[', '{');
        List<Character> closers = Arrays.asList(')', ']', '}');

        for (int i = 0; i < expression.length(); i++) {
            if (openers.contains(expression.charAt(i))) {
                stack.push(expression.charAt(i));
            }
            if (closers.contains(expression.charAt(i))) {
                // If stack is empty, then a closing bracket has no opening bracket pair, return false
                if (stack.isEmpty()) {
                    return false;
                }
                // If the closing bracket's pair-value is not the same as the top of the stack,
                // there's something wrong with pair-wise parentheses, return false
                if (!map.get(expression.charAt(i)).equals(stack.pop())) {
                    return false;
                }
            }
        }
        // if stack is not empty, there is at least one mismatched pair
        // otherwise, this will be true, and all pairs are accounted for
        return stack.isEmpty();
    }

    // Convert infix to postfix
    public static String infixToPostfix(String expression) {
        StackUsingLL<String> stack = new StackUsingLL<>();
        StringBuilder output = new StringBuilder();

        // Convert expression into tokens (each token is a single character, except for multi-digit numbers are a single token)
        List<String> tokens = tokenize(expression);

        // Helper Predicate to determine if the token is a number or single-letter variable
        Predicate<String> isOperand = (str) -> str != null && (str.matches("\\d+") || str.matches("[a-zA-Z]"));
        // helper Predicate to determine if the token is an operator [+ - * /]
        Predicate<String> isOperator = (str) -> str != null
                && (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/"));

        // for each token
        for (String token : tokens) {
            if (isOperand.test(token)) {
                // if token is an operand, just add it to the output
                output.append(token);
            } else if (token.equals("(")) {
                // if token is (, then put it on the stack
                stack.push(token);
            } else if (token.equals(")")) {
                // if the token is ), then pop off the stack until you hit (
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.append(stack.pop());
                }
                stack.pop(); // remove the paired "(" element from stack
            } else if (isOperator.test(token)) {
                // if the token is an operator
                // pop off the stack (operators or operands) until you hit an operator lower priority
                // NOTE: parenthesis are handled above, so no fatal errors here
                while (!stack.isEmpty() && operatorPriorityMDAS(stack.peek()) >= operatorPriorityMDAS(token)) {
                    output.append(stack.pop());
                }
                stack.push(token);
            }
        }
        // clear whatever remains on the stack
        while (!stack.isEmpty()) {
            output.append(stack.pop());
        }
        return output.toString();
    }

    // Helper function to convert infix to postfix method
    // Converts a String into a List<String> of each token (handles numbers as a single token)
    public static List<String> tokenize(String input) {
        // remove all white space, both encapsulated and not
        String expression = input.replaceAll("\\s", "");
        // normalize parentheses into ()
        expression = expression.replace("{", "(")
                .replace("}", ")")
                .replace("[", "(")
                .replace("]", ")");

        List<Character> digits = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        List<String> tokens = new ArrayList<>();
        int i = 0;
        while (i < expression.length()) {
            // Get the character at i
            char c = expression.charAt(i);
            // if c is a number, continue forward one character at a time
            if (digits.contains(c)) {
                StringBuilder numToken = new StringBuilder();
                // Keep going until the next character is not longer a number
                while (i < expression.length() && digits.contains(expression.charAt(i))) {
                    numToken.append(expression.charAt(i));
                    i++;
                }
                tokens.add(numToken.toString());
            } else {
                // if c is not a number, just add the operator/parenthesis/single-letter variable
                tokens.add((String.valueOf(c)));
                i++;
            }
        }
        return tokens;
    }

    // defines operator priority as seen in PEMDAS arithmetic
    public static int operatorPriorityMDAS(String operator) {
        return switch (operator) {
            case "*", "/" ->
                2;
            case "+", "-" ->
                1;
            default ->
                -1;
        };
    }

    // Evaluate postfix expression
    // NOTE: this method assumes that the output came from infixToPostfix()
    public static int evaluatePostfix(String postfix, Map<Character, Integer> variableMap) {
    StackUsingLL<Integer> stack = new StackUsingLL<>();
    for (int i = 0; i < postfix.length(); i++) {
        Character c = postfix.charAt(i); // get the character from the postFix expression

        // if its a variable, get the integer value from the variableMap and push it onto the stack
        if (Character.isLetter(c)) {
            if (!variableMap.containsKey(c)) {
                System.out.println("Variable " + c + " not found in map.");
                return -9999;
            }
            stack.push(variableMap.get(c));
        } else {
            // Get the operands from the stack
            int right = stack.pop();
            int left = stack.pop();

            // Compute the result
            int result = switch (c) {
                case '+' -> left + right;
                case '-' -> left - right;
                case '*' -> left * right;
                case '/' -> left / right;
                default -> {System.out.println("Something went wrong!"); yield -9999;}
            };
            stack.push(result); // put the result back onto the stack to be used as the next operand
        }
    }

    return stack.pop(); // final result
}

    // Convert Decimal to binary
    public static String convertDecimalToBinary(int num) {
        if (num == 0) {
            return "0";
        }
        if (num < 0) {
            return "Sorry, I can't handle negative numbers :(";
        }
        StackUsingLL<String> stack = new StackUsingLL<>();
        StringBuilder numAsBinary = new StringBuilder();
        // Recursively build stack
        populateStackWithBinaryDigits(stack, num);
        // While stack is not empty, pop elements and build the string representation
        while (!stack.isEmpty()) {
            numAsBinary.append(stack.pop());
        }
        return numAsBinary.toString();
    }

    // helper recursive function
    private static void populateStackWithBinaryDigits(StackUsingLL<String> stack, int num) {
        int quotient = num / 2;
        int remainder = num % 2;
        stack.push(String.valueOf(remainder));
        if (quotient != 0) {
            populateStackWithBinaryDigits(stack, quotient);
        }
    }

    // Convert binary to decimal
    public static int convertBinaryToDecimal(String input) {
        // Null check
        if (input == null) {
            return -9999;
        }

        // remove all white space, both encapsulated and not
        String binaryRepresentation = input.replaceAll("\\s", "");

        // if string is empty or null, return sentinel value -9999
        if (binaryRepresentation.isEmpty()) {
            return -9999;
        }

        // convert if the string has only 0 or 1
        if (binaryRepresentation.matches("[01]+")) {
            int multiplier = 1;
            int total = 0;
            for (int i = binaryRepresentation.length() - 1; i >= 0; i--) {
                int bit = binaryRepresentation.charAt(i) - '0';
                total += multiplier * bit;
                multiplier *= 2;
            }
            return (int) total;
        } else {
            System.out.println("something went wrong!");
            return -9999;
        }
    }
}
