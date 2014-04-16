package db.operation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.User;

import db.connection.ConnectionStatement;
import db.connection.PublicConnection;

public class UserOperation extends DBOperation{

	public static List<User> getOnlineUsers() {
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		open(connStmt);
		
		List<User> result = new ArrayList<User>();
		
		if(connStmt.connection != null){
			String sqlString = "select * from user where online=1";
			
			try {
				rs = connStmt.statement.executeQuery(sqlString);
			
				while(rs.next()){
					User user = new User();
					user.id = rs.getInt("id");
					user.username = rs.getString("username");
					user.password = rs.getString("password");
					user.resident = user.username;  //暂时和username相同
					user.online = rs.getInt("online");
					
					Timestamp ts = rs.getTimestamp("last_login");
					Date d = new Date();
					d.setTime(ts.getTime());
					user.lastLogin = d;
					
					result.add(user);
				}		
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
			close(connStmt);
		}
		return result;
	}

}
