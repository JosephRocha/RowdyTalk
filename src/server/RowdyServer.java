package server;
import java.net.*;
import java.util.ArrayList;

import MessageUtility.Message;

import java.io.*;
 
public class RowdyServer {
	static int portNumber = 9001;
	private static ArrayList<ObjectOutputStream> writers = new ArrayList<>();
    public static void main(String[] args) throws IOException {
         ServerSocket server = new ServerSocket(portNumber);
         
         try{
        	 while(true){
        		 new ServerHandler(server.accept()).start();
        	 }
          } catch (Exception e) {
             e.printStackTrace();
         } finally {
             server.close();
         }
    }
    
    
    public static class ServerHandler extends Thread{
   
    	public Socket socket;
    	ObjectOutputStream object_output_stream;
    	
    	 public ServerHandler(Socket socket) throws IOException {
             this.socket = socket;
         }
    	 
    	 public void run(){
    		 try(
    			InputStream input = socket.getInputStream();
    			ObjectInputStream object_input_stream = new ObjectInputStream(input);
    			OutputStream output = socket.getOutputStream();
    			ObjectOutputStream object_output_stream = new ObjectOutputStream(output);
    				 ){
    			 
    			 Message message = (Message) object_input_stream.readObject();
    			 writers.add(object_output_stream);
    			 broadcast(message);
    			 
    			 while(socket.isConnected()){
    				 Message input_message = (Message) object_input_stream.readObject();
    				 if(input_message != null){
    					 broadcast(input_message);
    				 }
    			 }
    			 
    		 } catch (IOException | ClassNotFoundException e) {
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
    
    

