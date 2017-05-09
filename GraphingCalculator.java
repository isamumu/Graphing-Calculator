import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//Graphing Calculator class
// use .matches . returns true if the string contains the pattern exactly
public class GraphingCalculator {
	private double x;
	
	private final static String VARIABLE_PATTERN = "[a-z]";
	private final static String NUMBER_PATTERN = "-?[0-9]+";
	private final static String FUNCTION_PATTERN = "[a-z]+\\(.+\\)";
	private final static String OPERATOR_PATTERN = "([a-z]|-?[0-9]+|\\(.+\\)|[a-z]+\\(.+\\)) ([+-/*]) ([a-z]|-?[0-9]+|\\(.+\\)|[a-z]+\\(.+\\))";

	public double variable() {
		return x;
	}

	public double number(String numInStringForm) {
		return Integer.parseInt(numInStringForm);
	}

	public double function(String name, String expression) {
		name = name.trim();
		expression = expression.trim();

		double expressionValue = calc(expression);
		if (name.equals("log")) {
			return Math.log10(expressionValue);
		} else if (name.equals("sin")) {
			return Math.sin(Math.toRadians(expressionValue));
		} else if (name.equals("cos")) {
			return Math.cos(Math.toRadians(expressionValue));
		} else if (name.equals("tan")) {
			return Math.tan(Math.toRadians(expressionValue));
		}
		// this should never happen
		return 0;
	}

	public double operator(String leftExpression, String rightExpression, String operatorSymbol) {
		leftExpression = leftExpression.trim();
		rightExpression = rightExpression.trim();
		operatorSymbol = operatorSymbol.trim();

		double leftExpressionValue = calc(leftExpression);
		double rightExpressionValue = calc(rightExpression);
		if (operatorSymbol.equals("+")) {
			return leftExpressionValue + rightExpressionValue;
		} else if (operatorSymbol.equals("-")) {
			return leftExpressionValue - rightExpressionValue;
		} else if (operatorSymbol.equals("*")) {
			return leftExpressionValue * rightExpressionValue;
		} else if (operatorSymbol.equals("/")) {
			return leftExpressionValue / rightExpressionValue;
		}

		// this should never happen
		return 0;
	}

	public double evaluate(String expression, double x){
		this.x = x;
		return this.calc(expression);
		
	}
	private double calc(String expression) {
		
		if(expression.matches(OPERATOR_PATTERN)){
			Pattern pattern = Pattern.compile(OPERATOR_PATTERN);
			Matcher matcher = pattern.matcher(expression);
			matcher.find();
			return operator(matcher.group(1), matcher.group(3), matcher.group(2));
		} else if (expression.matches(FUNCTION_PATTERN)) {
			return function(expression.substring(0, 3), expression.substring(4, expression.length() - 1));
		} else if (expression.matches(NUMBER_PATTERN)) {
			return number(expression);
		} else if (expression.matches(VARIABLE_PATTERN)) {
			return variable();
		}

		return -1;
	}
}
