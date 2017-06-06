package cn.net.cvtt.lz.common.algorithm;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.net.cvtt.lian.common.util.StringUtils;

/**
 * 
 * <b>描述: </b>中缀表达式也即正常的四则运算，这里把中缀表达式转化为后缀表达式计算
 * <p>
 * <b>举例: </b>6*(7-2*3)+(8-3)+20/4*2 ，变为后缀表达式：6 7 2 3 * - * 8 3 - + 20 4 2 / * +。
 * <p>
 * 
 * @author zongchuanqi
 * @see SuffixExpression
 */
public class InfixExpression {
	private static Stack<Integer> stack = new Stack<>();
	private static Queue<String> queue = new LinkedList<String>();
	private static final String numRegEx = "^[1-9]*$";
	private static final String operateSymbolRegEx = "[\\+\\-\\*\\/]";
	private static final String plusSymbol = "+";
	private static final String minusSymbol = "-";
	private static final String multipleSymbol = "*";
	private static final String divisionSymbol = "/";

	public static int calculateExpression(String expressionStr) {
		InfixExpression.readInput(expressionStr);
		String exchar = null;
		int result = 0;
		while ((exchar = queue.poll()) != null) {
			if (stack.size() < 2) {
				throw new RuntimeException("invalid expression");
			}
			int num1 = stack.pop();
			int num2 = stack.pop();
			if (exchar.equals(plusSymbol)) {
				result = num1 + num2;
			} else if (exchar.equals(minusSymbol)) {
				result = num1 - num2;
			} else if (exchar.equals(multipleSymbol)) {
				result = num1 * num2;
			} else if (exchar.equals(divisionSymbol)) {
				result = num1 / num2;
			}
			stack.push(result);
		}
		if (stack.size() > 1 && queue.isEmpty()) {
			throw new RuntimeException("invalid expression");
		}
		return stack.pop();
	}

	private static void readInput(String expressionStr) {
		if (StringUtils.isNullOrEmpty(expressionStr)) {
			return;
		}
		String[] splitExpressionStr = expressionStr.split(" ");
		Pattern numRegPattern = Pattern.compile(numRegEx);
		Pattern operateSymbolRegPattern = Pattern.compile(operateSymbolRegEx);
		for (String exchar : splitExpressionStr) {
			Matcher numRegMatcher = numRegPattern.matcher(exchar);

			Matcher operateSymbolRegMatcher = operateSymbolRegPattern.matcher(exchar);
			if (numRegMatcher.matches()) {
				stack.push(Integer.parseInt(exchar));
			} else if (operateSymbolRegMatcher.matches()) {
				queue.offer(exchar);
			}
		}
	}
}
