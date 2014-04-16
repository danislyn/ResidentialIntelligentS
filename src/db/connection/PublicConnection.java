package db.connection;

import java.sql.Connection;

public class PublicConnection {
	
	private static String user = "root";
	
	private static String password = "123123";
	
	private static String url = "jdbc:mysql://localhost:3306/residential_intelligent?useUnicode=true&characterEncoding=UTF8";
	
	private static String driver = "com.mysql.jdbc.Driver";
	
	private static DBConnectionPool pool;
	
	//静态初始化
	static
	{
		int maxCon = 50;
		pool = new DBConnectionPool(user, password, url, driver, maxCon);
	}
	
	
	/**
	 * 申请一个连接（默认最大等待时间为1000毫秒）
	 * @return Connection
	 */
	public static Connection getConnection(){
		//设置最大等待时间为1000毫秒
		return pool.getDBConnection(1000);
	}
	
	/**
	 * 归还一个连接
	 * @param connection 可用连接
	 */
	public static void freeConnection(Connection connection){
		pool.freeDBConnection(connection);
	}
	
}
