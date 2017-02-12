import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class RowdyClient {
	 String host_name;
     int port_number;
     
	public RowdyClient(String host, int port) {
		this.host_name = host;
		this.port_number = port;
	}
	
	public void run(){
		 try (
		            Socket kkSocket = new Socket(host_name, port_number);
		            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
		            BufferedReader in = new BufferedReader(
		                new InputStreamReader(kkSocket.getInputStream()));
		        ) {
		            BufferedReader stdIn =
		                new BufferedReader(new InputStreamReader(System.in));
		            String fromServer;
		            String fromUser;
		 
		            while ((fromServer = in.readLine()) != null) {
		                System.out.println("Server: " + fromServer);
		                if (fromServer.equals("Bye."))
		                    break;
		                 
		                fromUser = stdIn.readLine();
		                if (fromUser != null) {
		                    System.out.println("Client: " + fromUser);
		                    out.println(fromUser);
		                }
		            }
		        } catch (UnknownHostException e) {
		            System.err.println("Don't know about host " + host_name);
		            System.exit(1);
		        } catch (IOException e) {
		            System.err.println("Couldn't get I/O for the connection to " +
		                host_name);
		            System.exit(1);
		        }
		    }

}
