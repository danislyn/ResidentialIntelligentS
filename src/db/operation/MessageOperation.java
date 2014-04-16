package db.operation;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import beans.Message;

import db.connection.ConnectionStatement;

public class MessageOperation extends DBOperation {

	public static boolean saveMessage(Message msg){
		ConnectionStatement connStmt = new ConnectionStatement();
		open(connStmt);
		
		boolean result = false;
		
		if(connStmt.connection != null){
			Date d = new Date();
			Timestamp ts = new Timestamp(d.getTime());
			
			String sqlString = "insert into message (source_username, dest_username, type, content, create_time) " +
					"values('" + msg.sourceUsername + "', '" + msg.destUsername + "', " + msg.type.ordinal() + ", '" + msg.content + "', '" + ts + "')";
			
			try {
				connStmt.statement.executeUpdate(sqlString);
				result = true;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			close(connStmt);
		}
		return result;
	}
	
}
