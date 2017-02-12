package client;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import MessageUtility.Message;

public class RowdyClient implements Runnable {
	 String host_name;
     int port_number;
     String username;
     private ObjectOutputStream object_output_stream;
     private ObjectInputStream input_object_stream;
     private InputStream input;
     public static OutputStream output;
     public Socket socket;
     
	public RowdyClient(String host, int port, String user) {
		this.host_name = host;
		this.port_number = port;
		this.username = user;
	}
	
	public void run(){
		 try {
		            socket = new Socket(host_name, port_number);
		            output = socket.getOutputStream();   
		            object_output_stream = new ObjectOutputStream(output);
		            input = socket.getInputStream();
		            input_object_stream = new ObjectInputStream(input);
		    }catch(IOException e){
		    	System.out.println("Count Not Connect to IO");
		    }
		 
		 
		 try{
			 connect();
			 System.out.println("Connecting...");
			 while(socket.isConnected()){
				 Message message = null;
				 message = (Message) input_object_stream.readObject();
				 
				 if(message != null){
					 System.out.println(message.getOrigin()+": " + message.getMessage());
				 }
				 
			 }
		 }  catch (IOException | ClassNotFoundException e) {
			    System.err.println("BADBADBAD");
	            e.printStackTrace();
	        } 
	}
	
	public void connect() throws IOException{
		Message createMessage = new Message();
        createMessage.setOrigin(username);
        createMessage.setMessage("Connected");
        object_output_stream.writeObject(createMessage);
        System.out.println("Message Written To Output...");
	}

}