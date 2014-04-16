package db.operation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.Resource;

import db.connection.ConnectionStatement;

public class ResourceOperation extends DBOperation {

	public static List<Resource> getResources() {
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		open(connStmt);
		
		List<Resource> result = new ArrayList<Resource>();
		
		if(connStmt.connection != null){
			String sqlString = "select * from resource order by house_id, resource_type, end_date";
			
			try {
				rs = connStmt.statement.executeQuery(sqlString);
			
				while(rs.next()){
					Resource resource = new Resource();
					resource.id = rs.getInt("id");
					resource.houseId = rs.getString("house_id");
					resource.type = rs.getInt("resource_type");
					resource.total = rs.getFloat("total");
					
					Timestamp ts = rs.getTimestamp("end_date");
					Date d = new Date();
					d.setTime(ts.getTime());
					resource.endDate = d;
					
					result.add(resource);
				}		
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			close(connStmt);
		}
		
		//加入本期用量
		for(int i=1; i<result.size(); i++){
			Resource prev = result.get(i-1);
			Resource cur = result.get(i);
			if(cur.houseId.equals(prev.houseId) && cur.type == prev.type){
				cur.consumed = cur.total - prev.total;
			}
		}
		return result;
	}
	
}
