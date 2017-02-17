package client;

import java.io.IOException;
import java.util.ArrayList;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utility.Message;
import utility.MessageCell;
import utility.MessageType;
import utility.UserCell;

public class Client_GUI extends Application implements Runnable  {
	private String host_name;
    private int port_number;
    private String user_name;
    private Client client;
    private ListView<Message> history_list_view;
    private TextArea text;
    private ListView<String> user_list_view;
    private ObservableList<String> user_observable_list;
    private ObservableList<Message> history_observable_list;
    private ArrayList<Message> history = new ArrayList<Message>();
    
	public Client_GUI(String host, int port, String user, Client client) {
		this.host_name = host;
		this.port_number = port;
		this.user_name = user;
		this.client = client;
	}

	@Override
	public void start(Stage stage) throws Exception {
		Pane root = new Pane();
		root.setStyle("-fx-background-color: #0c2340");
		
		Scene scene = new Scene(root, 605, 450);
    	scene.getStylesheets().add("Res/Stylesheet.css");
    	
    	text = new TextArea();
    	text.setLayoutX(5);
    	text.setLayoutY(400);
    	text.setPrefSize(340, 45);
    	text.requestFocus();
    	root.getChildren().add(text);
    	
        history_list_view = new ListView<Message>();
    	history_list_view.setLayoutX(5);
    	history_list_view.setLayoutY(5);
    	history_list_view.setEditable(false);
    	history_list_view.setPrefSize(440, 385);
    	history_observable_list = FXCollections.observableArrayList();
        history_list_view.setItems(history_observable_list);
        history_list_view.setCellFactory((ListView<Message> l) -> new MessageCell());
        history_list_view.setStyle("-fx-background-color: transparent;");
    	root.getChildren().add(history_list_view);
    	
    	Button sendButton = new Button();
    	sendButton.setLayoutX(350);
    	sendButton.setLayoutY(400);
    	sendButton.setText("Send Message");
    	sendButton.setPrefSize(95, 45);
    	root.getChildren().add(sendButton);
    	
        user_list_view = new ListView<String>();
        user_list_view.setLayoutX(450);
        user_list_view.setLayoutY(5);
        user_list_view.setPrefSize(150, 440);
        user_observable_list = FXCollections.observableArrayList();
        user_list_view.setItems(user_observable_list);
        user_list_view.setStyle("");
        user_list_view.setCellFactory((ListView<String> l) -> new UserCell());
        root.getChildren().add(user_list_view);

    	stage.setTitle("RowdyTalk @" + host_name + ":" + port_number);
    	stage.setScene(scene);
    	
    	stage.show();
    	
    	history_list_view.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                event.consume();
            }
        });
    	
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
				
				if(message.getOrigin().equals(user_name)){
					message.setType(MessageType.SENT);
				}else{
				if(message.getOrigin().equals("SERVER")){
					message.setType(MessageType.SERVER_NOTIFICATION);
				}else{
					message.setType(MessageType.RECEIVED);
				}
			}
					
				history.add(message);
			
				history_observable_list.clear();
				history_observable_list = FXCollections.observableArrayList(history);
				history_list_view.setItems(history_observable_list);
				
				user_observable_list.clear();
				user_observable_list = FXCollections.observableArrayList(message.getUsers());
				user_list_view.setItems(user_observable_list);
				history_list_view.scrollTo(history.size()-1); 
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