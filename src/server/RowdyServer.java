package server;
import java.net.*;

import MessageUtility.Message;

import java.io.*;
 
public class RowdyServer {
	static int portNumber = 9001;
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
    			 object_output_stream.writeObject(message);
    			 System.out.println("Wrote Output Message...");
    			 while(socket.isConnected()){
    				 Message input_message = (Message) object_input_stream.readObject();
    				 if(input_message != null){
    					 System.out.println("HEY");
    				 }
    			 }
    			 
    		 } catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
    		 
    	 }
    }
    
 
    }
    
    

