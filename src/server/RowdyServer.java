package server;
import java.net.*;
import java.util.ArrayList;

import MessageUtility.Message;

import java.io.*;
 
public class RowdyServer {
	static int portNumber = 9001;
	private static ArrayList<ObjectOutputStream> writers = new ArrayList<ObjectOutputStream>();
	private static ArrayList<ServerHandler> handlers = new ArrayList<ServerHandler>();
    public static void main(String[] args) throws IOException {
         ServerSocket server = new ServerSocket(portNumber);
         
         try{
        	 while(true){
        		 ServerHandler newConnection = new ServerHandler(server.accept());
        		 handlers.add(newConnection);
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
	    			 broadcast(message);
	    			 
	    			 
	    			 while(socket.isConnected()){
	    				 Message input_message = (Message) object_input_stream.readObject();
	    				 if(input_message != null){
	    					 broadcast(input_message);
	    				 }
	    			 }
	    			 
				} catch (IOException | ClassNotFoundException e) {
					writers.remove(object_output_stream);
					e.printStackTrace();
				}
    	 }

    public void broadcast(Message message) throws IOException{
    	for(ObjectOutputStream writer: writers){
    		writer.writeObject(message);
    	}
    }
  }
}
    
    

