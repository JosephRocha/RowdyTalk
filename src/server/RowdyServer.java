package server;
import java.net.*;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import utility.Message;
import java.io.*;
 
public class RowdyServer {
	private static int portNumber = 9001;
	private static ArrayList<ObjectOutputStream> writers = new ArrayList<ObjectOutputStream>();
	private static ArrayList<String> users = new ArrayList<String>();
	
    public static void main(String[] args) throws IOException {
         ServerSocket server = new ServerSocket(portNumber);
         
         try{
        	 while(true){
        		 ServerHandler newConnection = new ServerHandler(server.accept());
        		 newConnection.start();
        	 }
          } catch (Exception e) {
             e.printStackTrace();
         } finally {
             server.close();
         }
    }
    
    public static class ServerHandler extends Thread{
    	String username;
    	public Socket socket;
    	
    	 public ServerHandler(Socket socket) throws IOException {
             this.socket = socket;
         }
    	 
    	 public void run(){
    			InputStream input = null;
    			ObjectInputStream object_input_stream = null;
    			OutputStream output = null;
    			ObjectOutputStream object_output_stream = null;
   
				try {
					input = socket.getInputStream();
					object_input_stream = new ObjectInputStream(input);
					output = socket.getOutputStream();
	    			object_output_stream = new ObjectOutputStream(output);
	    			
	    			 Message message = (Message) object_input_stream.readObject();
	    			 writers.add(object_output_stream);
	    			 username = message.getOrigin();
	    			 users.add(username);
	    			 message.setOrigin("SERVER");
	    			 message.setMessage(username + " has connected");
	    			 broadcast(message);
	    			 
	    			 
	    			 while(socket.isConnected()){
	    				 Message input_message = (Message) object_input_stream.readObject();
	    				 if(input_message != null){
	    					 broadcast(input_message);
	    				 }
	    			 }
	    			 
				} catch (IOException | ClassNotFoundException e) {
					writers.remove(object_output_stream);
					users.remove(username);
					Message message = new Message();
					message.setOrigin("SERVER");
					message.setMessage(username + " has disconnected");
					try {
						broadcast(message);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
    	 }

    public void broadcast(Message message) throws IOException{
    	message.userList.addAll(users);
    	for(ObjectOutputStream writer: writers){
    		writer.writeObject(message);
    	}
    }
     
  }
}