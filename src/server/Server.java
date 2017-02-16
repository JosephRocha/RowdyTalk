package server;
import java.net.*;
import java.util.ArrayList;
import utility.Message;
import java.io.*;
 
public class Server {
	private static int portNumber = 3423;
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
    	boolean kicked = false;
    	
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
	    			 username = message.getOrigin();
	    			 
	    			 if(username.equals("SERVER") || users.contains(username)){
	    				 Message IllegalUsername = new Message();
	    				 IllegalUsername.setOrigin("SERVER");
	    				 IllegalUsername.setMessage("Username is already active or reserved on this server, please exit and try again.");
	    				 IllegalUsername.user_list.addAll(users);
	    				 object_output_stream.writeObject(IllegalUsername);
	    				 socket.close();
	    				 kicked = true;
	    				 
	    			 }else{
	    				 writers.add(object_output_stream);
		    			 users.add(username);
		    			 message.setOrigin("SERVER");
		    			 message.setMessage(username + " has connected");
		    			 broadcast(message);
	    			 }
	    			 
	    			 
	    			 while(socket.isConnected()){
	    				 Message input_message = (Message) object_input_stream.readObject();
	    				 if(input_message != null){
	    					 broadcast(input_message);
	    				 }
	    			 }
	    			 
				} catch (IOException | ClassNotFoundException e) {
					if(!kicked){
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
    	 }

    public void broadcast(Message message) throws IOException{
    	message.user_list.addAll(users);
    	for(ObjectOutputStream writer: writers){
    		writer.writeObject(message);
    	}
    }
     
  }
}