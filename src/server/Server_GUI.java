package server;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Server_GUI extends Application implements Runnable  {
    private int port_number;
    
	public Server_GUI(int port) {
		this.port_number = port;
	}

	@Override
	public void start(Stage stage) throws Exception {
		Pane root = new Pane();
		root.setStyle("-fx-background-color: #0c2340");
		
		Scene scene = new Scene(root, 605, 450);
    	scene.getStylesheets().add("Res/Stylesheet.css");


    	stage.setTitle("RowdyTalk Server @" + port_number);
    	stage.setScene(scene);
    	
    	stage.show();
    	  
    	  stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
              public void handle(WindowEvent we) {
                  System.exit(0);
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