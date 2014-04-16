package beans;

import java.util.Date;

public class Resource {

	public int id;  //auto incremental ID
	public String houseId;  //房号
	public int type;  //1:水  2:电
	public float total;  //截止日期时的总用量
	public Date endDate;
	
	public float consumed;
	
}
