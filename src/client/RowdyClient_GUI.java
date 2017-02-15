package client;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utility.Message;

public class RowdyClient_GUI extends Application implements Runnable  {
	public String host_name;
    public int port_number;
    public String username;
    public Stage ClientChatGUI;
    public RowdyClient client;
    public TextArea history;
    public TextArea text;
    ListView<String> userListView;
    ObservableList<String> userObservableList;
    
	public RowdyClient_GUI(String host, int port, String user, RowdyClient client) {
		this.host_name = host;
		this.port_number = port;
		this.username = user;
		this.client = client;
	}

	@Override
	public void start(Stage stage) throws Exception {
    	text = new TextArea();
    	text.setLayoutX(5);
    	text.setLayoutY(400);
    	text.setPrefSize(340, 45);
    	
        history = new TextArea();
    	history.setLayoutX(5);
    	history.setLayoutY(5);
    	history.setEditable(false);
    	history.setPrefSize(440, 385);
    	history.setWrapText(true);
    	
    	
    	Button sendButton = new Button();
    	sendButton.setLayoutX(350);
    	sendButton.setLayoutY(400);
    	sendButton.setText("Send Message");
    	sendButton.setPrefSize(95, 45);
    	
        userListView = new ListView<String>();
        userListView.setLayoutX(450);
        userListView.setLayoutY(5);
        userListView.setPrefWidth(150);
        userListView.setPrefHeight(440);
        userListView.setDisable(true);
        userListView.setItems(userObservableList);

    	Pane root = new Pane();
		root.setStyle("-fx-background-color: #0c2340");
    	stage.setTitle("RowdyTalk @" + host_name + ":" + port_number);
    	Scene scene = new Scene(root, 605, 450);
    	scene.getStylesheets().add("Res/Stylesheet.css");
    	stage.setScene(scene);
    	
    
    	root.getChildren().addAll(history, text, userListView, sendButton);
    	stage.show();
    	
    	text.requestFocus();
    	text.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                	try {
						client.sendMessage(text.getText());
	              		text.clear();
	              		ke.consume();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        });
    	
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
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				history.appendText(message.getOrigin() + ": " + message.getMessage() + "\n");
				userObservableList = FXCollections.observableArrayList(message.getUsers());
				userObservableList.clear();
				userObservableList = FXCollections.observableArrayList(message.getUsers());
				userListView.setItems(userObservableList);
			}
		});
	}
	
	@Override
	public void run() {
			try {
				start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}