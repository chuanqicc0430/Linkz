package cn.net.cvtt.lz.common.algorithm;

public class SuffixExpressionTest {
	public static void main(String[] args) {
		System.out.println(6*(7-2*3)+(8-3)+20/5*2);
		System.out.println(SuffixExpression.calculateExpression("6 7 2 3 * - * 8 3 - + 20 5 / 2 * +"));
	}
}
