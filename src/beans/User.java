package beans;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	
	public int id;
	public String username;
	public String password;
	public String resident;  //ס������ʱ��username��ͬ��
	public int online;  //0: offline; 1: online
	public Date lastLogin;

}
