import java.beans.Expression;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// use .matches . returns true if the string contains the pattern exactly
//note: inside of brackets always separate the operator with spaces on both sides
//regex patterns are used to detect types of functions

//this class acts as the brain of the graphing calculator
public class GraphingCalculator {
	private double x;
	// create regex patterns
	private final static String GROUP_PATTERN = "(\\()(.+)(\\))";
	private final static String VARIABLE_PATTERN = "[a-z]";
	private final static String NUMBER_PATTERN = "-?[0-9]+";
	private final static String FUNCTION_PATTERN = "[a-z]+\\(.+\\)";
	private final static String OPERATOR_PATTERN = "([a-z]|-?[0-9]+|\\(.+\\)|[a-z]+\\(.+\\)|[a-z]\\^[0-9]) ([+-/*^]) ([a-z]|-?[0-9]+|\\(.+\\)|[a-z]+\\(.+\\)|[a-z]\\^[0-9])$";

	// the following booleans help set a scale for the graph. Without it, every
	// point will be measured in pixels
	boolean isPower = false; // did we detect a power function?
	boolean isCos = false; // did we detect a cos function?
	boolean isSin = false; // did we detect a sin function?
	boolean isTan = false; // did we detect a tan function?
	boolean isLog = false; // did we detect a log function

	// group function for operations inside paranthesis
	public double group(String expression) {
		String arguments = expression.substring(1, expression.length() - 1);
		System.out.println(arguments);
		Pattern pattern = Pattern.compile(OPERATOR_PATTERN);
		Matcher matcher = pattern.matcher(arguments);
		matcher.find();
		System.out.println(matcher.group(1) + " . " + matcher.group(3));
		return operator(matcher.group(1), matcher.group(3), matcher.group(2));
	}

	// if the function is just a variable return the value of the variable
	public double variable() {
		return x;
	}

	// if the function is a number, return the number
	public double number(String numInStringForm) {
		return Integer.parseInt(numInStringForm); // parse from string to int
	}

	// if the function is a trig function or log function, evaluate the y values
	public double function(String name, String expression) {
		name = name.trim(); // remove spaces
		expression = expression.trim(); // remove spaces

		// for trig functions, one unit equals 10 positions (100 pixels)
		// angles on the graph are measured in degrees. Each tick representing
		// 10 degrees
		// Math.sin, Math.cos, and Math.tan use radians. Therefore toRadians is
		// used to convert degrees to radians
		double expressionValue = calc(expression);
		if (name.equals("log")) {
			isLog = true;
			return Math.log10(expressionValue); // evaluate log base ten
		} else if (name.equals("sin")) {
			isSin = true;
			return Math.sin(Math.toRadians(expressionValue)); // evaluate sin
		} else if (name.equals("cos")) {
			isCos = true;
			return Math.cos(Math.toRadians(expressionValue)); // evaluate cos
		} else if (name.equals("tan")) {
			isTan = true;
			return Math.tan(Math.toRadians(expressionValue)); // evaluate tan
		}
		// this should never happen
		return 0;
	}

	// if the function is an operation, we evaluate the left and right side of
	// the expression, separated by an operator, using the calc method
	public double operator(String leftExpression, String rightExpression, String operatorSymbol) {
		leftExpression = leftExpression.trim(); // remove spaces
		rightExpression = rightExpression.trim(); // remove spaces
		operatorSymbol = operatorSymbol.trim(); // remove spaces

		double leftExpressionValue = calc(leftExpression); // calculate the left
															// side of the
															// expression
		double rightExpressionValue = calc(rightExpression); // calculate the
																// right side of
																// the
																// expression

		// detect the type of operator and evaluate the operation
		if (operatorSymbol.equals("+")) {
			return leftExpressionValue + rightExpressionValue; // add left and
																// right
		} else if (operatorSymbol.equals("-")) {
			return leftExpressionValue - rightExpressionValue; // subtract left
																// and right
		} else if (operatorSymbol.equals("*")) {
			return leftExpressionValue * rightExpressionValue; // multiply left
																// and right
		} else if (operatorSymbol.equals("/")) {
			return leftExpressionValue / rightExpressionValue; // divide left
																// and right
		} else if (operatorSymbol.equals("^")) {
			isPower = true;
			return Math.pow(leftExpressionValue, rightExpressionValue); // evaluate
																		// the
																		// value
																		// of
																		// the
																		// base
																		// and
																		// its
																		// power
		}

		// this should never happen
		return 0;
	}

	// this method will be used in the GraphingPanel to loop through every x
	// value and its corresponding y value
	public double evaluate(String expression, double x) {
		this.x = x;
		return this.calc(expression);

	}

	// this method checks to see if the inputed expression is valid or not
	public boolean isValid(String expression) {

		// if none of these patterns are detected, the function is invalid
		return expression != null && (expression.matches(OPERATOR_PATTERN) || expression.matches(FUNCTION_PATTERN)
				|| expression.matches(NUMBER_PATTERN) || expression.matches(VARIABLE_PATTERN)
				|| expression.matches(GROUP_PATTERN));

	}

	// in the calc method, values are returned based on the pattern detected
	private double calc(String expression) {

		if (expression.matches(OPERATOR_PATTERN)) {
			Pattern pattern = Pattern.compile(OPERATOR_PATTERN);
			Matcher matcher = pattern.matcher(expression);
			matcher.find();
			// call and return the value returned by the operator function
			return operator(matcher.group(1), matcher.group(3), matcher.group(2));
		} else if (expression.matches(FUNCTION_PATTERN)) {
			// call and return the value returned by the function method
			return function(expression.substring(0, 3), expression.substring(4, expression.length() - 1));
		} else if (expression.matches(NUMBER_PATTERN)) {
			// call and return the value returned by the number method
			return number(expression);
		} else if (expression.matches(VARIABLE_PATTERN)) {
			// call and return the value returned by the variable method
			return variable();
		} else if (expression.matches(GROUP_PATTERN)) {
			// call and return the value returned by the group method
			return group(expression);
		}

		return -1; // this should never happen
	}
}
