package db.operation;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.connection.ConnectionStatement;
import db.connection.PublicConnection;

public class DBOperation {

	//�����ݿ�ȡ�����ӣ���ʼ��statement
	public static void open(ConnectionStatement connectionStatement){
		try {
			//�����ݿ�ȡ�����ӣ��������ȴ�ʱ��Ϊ1000����
			connectionStatement.connection = PublicConnection.getConnection();
			
			//��ʼ��statement
			if(connectionStatement.connection != null){
				connectionStatement.statement = connectionStatement.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			}
			else{
				System.out.println("no available connection in ConnectionPool, wait......");
			}
		}catch(SQLException e){			
			System.out.println("Exception when open().....");
			e.printStackTrace();
		}
			
	}
	
	//�黹Connection
	public static void close(ConnectionStatement connectionStatement){
		if(connectionStatement.statement != null)
			try {
				connectionStatement.statement.close();
			} catch (SQLException e) {
				System.out.println("Exception when close().....");
				e.printStackTrace();			
			}
		
		if(connectionStatement.connection != null)
			PublicConnection.freeConnection(connectionStatement.connection);
	}
	
}
