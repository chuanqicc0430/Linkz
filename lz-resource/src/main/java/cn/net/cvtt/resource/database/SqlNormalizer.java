package cn.net.cvtt.resource.database;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.Token;

/**
 * <b>描述：</b>对sql语句进行归一化处理，便于统计分析用
 * <p>
 * <b>功能：</b>对sql语句进行归一化处理
 * <p>
 * <b>用法：</b>
 * <p>
 * <pre><code>
 * String sql = "select * from UP_User where UserId=1 and name like '%li%' ";
 * String formatSql = SqlNormalizer.format(sql);
 * 得出的formatSql = "select * from UP_User where UserId=? and name like '%?%'"
 * </code></pre>
 * 
 * @author 
 *
 */

public class SqlNormalizer {

	public static String format(String sql)
	{
		if(sql == null || sql.equals(""))
			return sql;
		ANTLRStringStream input = new ANTLRStringStream(sql);
		PLSQLLexer lexer = new PLSQLLexer(input);
		StringBuilder sbSql = new StringBuilder();
		Token t = lexer.nextToken();
		while(t != Token.EOF_TOKEN)
		{
			if(t.getType() == PLSQLLexer.CHAR_STRING || t.getType() == PLSQLLexer.EXACT_NUM_LIT || t.getType() == PLSQLLexer.UNSIGNED_INTEGER)
			{
				sbSql.append("?");
			}
			else
				sbSql.append(t.getText());
			
			t = lexer.nextToken();
		}
		return sbSql.toString();
	}
}
