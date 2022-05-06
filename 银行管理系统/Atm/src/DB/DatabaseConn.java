package DB;

import java.sql.*;

public class DatabaseConn {
	private Statement stmt = null;
	ResultSet rs = null;
	private  Connection conn = null;
	String sql;
	 static final String driverName="com.mysql.jdbc.Driver";

	 static final String dbURL="jdbc:mysql://localhost:3306/atm123?user=root&password=123456&useUnicode=true&characterEncoding=UTF-8";

	 static final String userName="root";

	 static final String userPwd="123456";
	public DatabaseConn() {}

	public  void OpenConn() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			/*这里原来有问题，控制台抛出了异常，而后我依据此处抛出异常断定此处是问题所在，
			 * 那肯定没导入驱动库jar文件，便网上下载并导入了mysql-connector-java-3.1.14-bin.jar,解决了
			 */
			 System.out.println("成功加载MySQL驱动程序");
			conn = DriverManager.getConnection(dbURL);
		} catch (Exception e) {
			System.out.println("OpenConn:" + e.getMessage());
		}
	}

	public ResultSet executeQuery(String sql) {
		stmt = null;
		rs = null;
		try {
			conn = DriverManager.getConnection(dbURL);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.err.println("查询数据:" + e.getMessage());
		}
		return rs;
	}

	public void executeUpdate(String sql) {
		stmt = null;
		rs = null;
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.executeUpdate(sql);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("更新数据:" + e.getMessage());
		}
	}

	public void closeStmt() {
		try {
			stmt.close();
		} catch (SQLException e) {
			System.err.println("释放对象:" + e.getMessage());
		}
	}

	public void closeConn() {
		try {
			conn.close();
		} catch (SQLException ex) {
			System.err.println("释放对象:" + ex.getMessage());
		}
	}

	public static String toUTF(String str) {
		try {
			if (str == null)
				str = "";
			else
				str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (Exception e) {
			System.out.println(e);
		}
		return str;
	}
}
