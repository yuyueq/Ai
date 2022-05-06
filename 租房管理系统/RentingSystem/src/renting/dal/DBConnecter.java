package renting.dal;

/*
数据库连接
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class DBConnecter {
	/*
	 * 连接数据库
	 */
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/admin?serverTimezone=UTC&characterEncoding=utf-8";
	private String user = "root";
	private String password = "123456";

	public Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (java.lang.ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			System.out.println("驱动失败");
		} catch (SQLException ex) {
			System.out.println("数据库登录失败!");
		}

		// conn=DriverManager.getConnection(URL, "root", "12481632");

		return conn;
	}
	/*
	
	*/
}
