package utility;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {

	private String message_content;
	private String origin_user;
	public ArrayList<String> user_list = new ArrayList<String>();
	private MessageType type;
	
	public Message(){	
	}
	
	public void setMessage(String message){
		this.message_content = message;
	}
	
	public void setOrigin(String origin_user){
		this.origin_user = origin_user;
	}
	
	public void setUsers(ArrayList<String> users){
	}
	
	public String getMessage(){
		return message_content;
	}
	
	public String getOrigin(){
		return origin_user;
	}
	
	public ArrayList<String> getUsers(){
		return user_list;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}
}
