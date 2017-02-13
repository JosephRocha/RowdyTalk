package client;

import MessageUtility.Message;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
    	history.setLayoutX(5);
    	history.setLayoutY(5);
    	history.setEditable(false);
    	history.setPrefSize(440, 385);
    	history.setWrapText(true);
    	
    	TextArea text = new TextArea();
    	text.setLayoutX(5);
    	text.setLayoutY(400);
    	text.setPrefSize(340, 45);
    	
    	
    	Button sendButton = new Button();
    	sendButton.setLayoutX(350);
    	sendButton.setLayoutY(400);
    	sendButton.setText("Send Message");
    	sendButton.setPrefSize(95, 45);
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
    	  
    	  stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
              public void handle(WindowEvent we) {
                  System.exit(0);
              }
          });      
	}
	
	public void displayMessage(Message message){
		history.appendText(message.getOrigin() + ": " + message.getMessage() + "\n");	
	}
	
	@FXML
    public void closeApplication() {
		
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