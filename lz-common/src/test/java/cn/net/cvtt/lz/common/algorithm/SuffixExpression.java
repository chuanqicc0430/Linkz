package cn.net.cvtt.lz.common.algorithm;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.net.cvtt.lian.common.util.StringUtils;

/**
 * 
 * <b>描述: </b>后缀表达式：给定一个字符串，从左到右扫描，把每个操作符应用到其之前的两个紧邻数字，并用该计算结构替代该操作符，最后，就剩下该表达式的最终值了
 * <p>
 * <b>举例: </b>4 5 2 * + ，从左到右扫描，知道遇到*，把该操作符应用到之前的两个数字，5,2，并用计算结果，5*2 = 10替换该操作符，变为 4 10 +，继续计算，得到4+10=14。
 * <p>
 * 
 * @author zongchuanqi
 */
public class SuffixExpression {
	private static Stack<Integer> stack = new Stack<>();
	private static Queue<String> queue = new LinkedList<String>();
	private static final String numRegEx = "^[1-9]\\d*$";
	private static final String operateSymbolRegEx = "[\\+\\-\\*\\/]";
	private static final String plusSymbol = "+";
	private static final String minusSymbol = "-";
	private static final String multipleSymbol = "*";
	private static final String divisionSymbol = "/";

	public static int calculateExpression(String expressionStr) {
		SuffixExpression.readInput(expressionStr);
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
