package client;

import MessageUtility.Message;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RowdyClient_GUI extends Application implements Runnable  {
	public String host_name;
    public int port_number;
    public String username;
    public Stage ClientChatGUI;
    RowdyClient client;
    public TextArea history;
    
	public RowdyClient_GUI(String host, int port, String user, RowdyClient client) {
		this.host_name = host;
		this.port_number = port;
		this.username = user;
		this.client = client;
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		Pane root = new Pane();
		root.setStyle("-fx-background-color: #0c2340");
    	stage.setTitle("RowdyTalk @" + host_name + ":" + port_number);
    	stage.setScene(new Scene(root, 450, 450));
    	
        history = new TextArea();
    	history.setStyle("-fx-background-color: #B7B1A9");
    	history.setLayoutX(0);
    	history.setLayoutY(0);
    	history.setEditable(false);
    	history.setPrefSize(450, 340);
    	
    	
    	TextArea text = new TextArea();
    	text.setLayoutX(0);
    	text.setLayoutY(350);
    	text.setPrefSize(400, 100);
    	
    	
    	Button sendButton = new Button();
    	sendButton.setLayoutX(350);
    	sendButton.setLayoutY(350);
    	sendButton.setText("Send Message");
    	sendButton.setPrefSize(100, 100);
    	sendButton.setStyle("-fx-background-color: #f15a22; -fx-text-fill: #0c2340;");

    	
    	root.getChildren().addAll(history, text, sendButton);
    	stage.show();
    	  sendButton.setOnAction(new EventHandler<ActionEvent>() {
              @Override public void handle(ActionEvent e) {
              	try {
              		client.sendMessage(text.getText());
              		text.clear();
  				} catch (Exception e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
              }
          });
	}
	
	public void displayMessage(Message message){
		history.appendText(message.getOrigin() + ": " + message.getMessage() + "\n");	
	}
	
	@Override
	public void run() {
		try {
			start(new Stage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
