package client;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
 
public class RowdyTalk extends Application {
 	static Label username_label;
	static TextField username_field;
	static Label hostname_label;
	static TextField hostname_field;
	static Label port_label;
	static TextField port_field;
	static Button login_button;
	static Label rowdytalk_heading;
	static Stage stage;
	
    @Override
    public void start(Stage primaryStage) {
    	
    	stage = primaryStage;
    	username_label = new Label("Username: ");
    	username_field = new TextField("Rocha" + Math.random());
    	hostname_label = new Label("Hostname: ");
    	hostname_field = new TextField("localhost");
    	port_label = new Label("Port: ");
    	port_field = new TextField("9001");
    	login_button = new Button();
    	rowdytalk_heading = new Label("RowdyTalk");
    	
    	username_label.setStyle("-fx-text-fill: #f15a22;");
    	username_label.setLayoutX(50);
    	username_label.setLayoutY(100);
    	
    	username_field.setLayoutX(120);
    	username_field.setLayoutY(97);
    	
    	hostname_label.setStyle("-fx-text-fill: #f15a22;");
    	hostname_label.setLayoutX(50);
    	hostname_label.setLayoutY(150);
       
    	hostname_field.setLayoutX(120);
    	hostname_field.setLayoutY(147);
    	
    	port_label.setStyle("-fx-text-fill: #f15a22;");
    	port_label.setLayoutX(85);
    	port_label.setLayoutY(200);
       
    	port_field.setLayoutX(120);
    	port_field.setLayoutY(197);
    	
    	login_button.setLayoutX(50);
    	login_button.setLayoutY(250);
    	login_button.setText("Connect To RowdyTalk");
    	login_button.setPrefSize(225, 25);
    	login_button.setStyle("-fx-background-color: #f15a22; -fx-text-fill: #0c2340;");
    	
    	rowdytalk_heading.setStyle("-fx-text-fill: #f15a22;");
    	rowdytalk_heading.setLayoutX(50);
    	rowdytalk_heading.setLayoutY(15);
    	rowdytalk_heading.setFont(new Font(45));
        
        Pane root = new Pane();
        root.getChildren().addAll(username_label, username_field, hostname_label, hostname_field, port_label, port_field, login_button, rowdytalk_heading);
        Scene scene = new Scene(root, 300, 400);
        root.setStyle("-fx-background-color: #0c2340");
        primaryStage.setTitle("RowdyTalk Beta 1.0");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        root.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                	connect();
                }
            }
        });
          
        login_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	connect();
            }
        });
    }
    //Note: There Appears to be a bug where the client thread begins before the gui thread has finished
    //In this case we get a weird error Exception in thread "JavaFX Application Thread" java.lang.ArrayIndexOutOfBoundsException: -1
    public static void connect(){
    	stage.hide();
    	RowdyClient client = new RowdyClient(hostname_field.getText(), Integer.parseInt(port_field.getText()), username_field.getText());
    	Thread x = new Thread(client);
    	RowdyClient_GUI gui = new RowdyClient_GUI(hostname_field.getText(), Integer.parseInt(port_field.getText()), username_field.getText(), client);
    	client.setGUI(gui);
    	try {
			gui.start(new Stage());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	x.start();
    }
    
 public static void main(String[] args) {
        launch(args);
    }
}