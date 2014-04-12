package beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable {

	public int id;  //auto incremental ID
	
	public String sourceUsername;  //from User
	public String sourceResident;  //from User
	public String destUsername;  //dest User, "ADMIN" is special case
	
	public MessageType type;  //0: CHAT; 1: ALARM
	
	public String content;
	public Date createTime;
	public int replyMsgId;  //用于回复
	
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	@Override
	public String toString(){
		return sourceUsername + "(房号" + sourceResident + "): " + content + "        " + sdf.format(createTime);
	}
	
}
