package beans;

import java.io.Serializable;

public class Message implements Serializable {

	public String source;
	public String content;
	
	@Override
	public String toString(){
		return source + ": " + content;
	}
	
}
