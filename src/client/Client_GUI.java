package client;

import java.io.File;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
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
    private FileChooser fileChooser = new FileChooser();
    
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
    	text.setPrefSize(385, 45);
    	text.requestFocus();
    	text.setWrapText(true);
    	root.getChildren().add(text);
    	
        history_list_view = new ListView<Message>();
    	history_list_view.setLayoutX(5);
    	history_list_view.setLayoutY(5);
    	history_list_view.setEditable(false);
    	history_list_view.setPrefSize(440, 390);
    	history_observable_list = FXCollections.observableArrayList();
        history_list_view.setItems(history_observable_list);
        history_list_view.setCellFactory((ListView<Message> l) -> new MessageCell());
        history_list_view.setStyle("-fx-background-color: transparent;");
      
    	root.getChildren().add(history_list_view);
    	
    	Image image = new Image("Res/camera.png");
        ImageView imageView = new ImageView(image);
        //imageView.setPreserveRatio(true);
        
        imageView.setFitWidth(35);
        imageView.setFitHeight(35);
    	Button sendButton = new Button("",imageView);
    	sendButton.setLayoutX(395);
    	sendButton.setLayoutY(400);
    	sendButton.setText("");
    	sendButton.setPrefSize(45, 44);
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

        history_list_view.prefWidthProperty().bind(root.widthProperty().subtract(user_list_view.widthProperty()).subtract(10));
        history_list_view.prefHeightProperty().bind(root.heightProperty().subtract(text.heightProperty().add(15)));
        user_list_view.layoutXProperty().bind(root.widthProperty().subtract(155));
        user_list_view.prefHeightProperty().bind(root.heightProperty().subtract(10));
        text.layoutYProperty().bind(root.heightProperty().subtract(text.heightProperty().add(5)));
        text.prefWidthProperty().bind(root.widthProperty().subtract(user_list_view.widthProperty().add(20).add(sendButton.widthProperty())));
        sendButton.layoutXProperty().bind(user_list_view.layoutXProperty().subtract(sendButton.widthProperty()).subtract(5));
        sendButton.layoutYProperty().bind(root.heightProperty().subtract(sendButton.heightProperty()).subtract(6));
        
        
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
            	  File file = fileChooser.showOpenDialog(stage);
            	  if(file != null){
            		  try {
            		  Image image = new Image("file:///" + file.getAbsolutePath());
            		  client.sendPhoto(image);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
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
				if(message.getOrigin().equals(user_name))
					message.setType(MessageType.SENT);
				else	
				if(message.getOrigin().equals("SERVER"))
					message.setType(MessageType.SERVER);
				else
				if(message.getOrigin().equals("ERROR"))
					message.setType(MessageType.ERROR);
				else
				if(message.getOrigin().equals("Watson"))
					message.setType(MessageType.BOT);
				else	
				message.setType(MessageType.RECEIVED);
					
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