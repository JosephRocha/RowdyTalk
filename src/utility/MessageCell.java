package utility;

import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MessageCell extends ListCell<Message> {
	@Override
    public void updateItem(Message item, boolean empty) {
      super.updateItem(item, empty);
      if (item == null || empty) {
    	  setGraphic(null);
      }else{
          VBox node = new VBox();
          Text text  = new Text(); 
          if(item.getType() == MessageType.SERVER){
        	  node.setId("serverMessage");
        	  text.setText(item.getMessage() + "\n");
    	  }
          if(item.getType() == MessageType.SENT){
        	  text.setText(item.getOrigin() + ": " + item.getMessage() + "\n");
        	  node.setId("sentMessage");
    	  }
          if(item.getType() == MessageType.RECEIVED){
        	  text.setText(item.getOrigin() + ": " + item.getMessage() + "\n");
        	  node.setId("receivedMessage");
    	  }
          if(item.getType() == MessageType.ERROR){
        	  text.setText(item.getMessage() + "\n");
        	  node.setId("errorMessage");
    	  }
          if(item.getType() == MessageType.BOT){
        	  text.setText(item.getOrigin() + ": " + item.getMessage() + "\n");
        	  node.setId("botMessage");
    	  }
          node.getChildren().add(text);
          if(item.isImage()){
        	  text.setText(item.getOrigin() + ": " );
        	  ImageView image_view = new ImageView();
        	  image_view.setPreserveRatio(true);
        	  image_view.fitWidthProperty().bind(this.widthProperty().subtract(20));
        	  image_view.setFitHeight(item.getImage().getHeight());
        	  image_view.setImage(item.getImage());
              node.getChildren().add(image_view);
    	  }
    	node.setAlignment(Pos.CENTER_LEFT);
    	text.wrappingWidthProperty().bind(this.widthProperty().subtract(20));
        setGraphic(node);
      }
    }
}