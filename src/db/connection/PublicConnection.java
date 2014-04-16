package db.connection;

import java.sql.Connection;

public class PublicConnection {
	
	private static String user = "root";
	
	private static String password = "123123";
	
	private static String url = "jdbc:mysql://localhost:3306/residential_intelligent?useUnicode=true&characterEncoding=UTF8";
	
	private static String driver = "com.mysql.jdbc.Driver";
	
	private static DBConnectionPool pool;
	
	//��̬��ʼ��
	static
	{
		int maxCon = 50;
		pool = new DBConnectionPool(user, password, url, driver, maxCon);
	}
	
	
	/**
	 * ����һ�����ӣ�Ĭ�����ȴ�ʱ��Ϊ1000���룩
	 * @return Connection
	 */
	public static Connection getConnection(){
		//�������ȴ�ʱ��Ϊ1000����
		return pool.getDBConnection(1000);
	}
	
	/**
	 * �黹һ������
	 * @param connection ��������
	 */
	public static void freeConnection(Connection connection){
		pool.freeDBConnection(connection);
	}
	
}
