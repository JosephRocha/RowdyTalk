package utility;

import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class MessageCell extends ListCell<Message> {
	@Override
    public void updateItem(Message item, boolean empty) {
      super.updateItem(item, empty);
      if (item == null || empty) {
    	  setGraphic(null);
    	  setStyle("");
      }else{
          HBox node = new HBox();
          Text text  = new Text(item.getOrigin() + ": " + item.getMessage() + "\n"); 
          if(item.getType() == MessageType.SERVER_NOTIFICATION){
        	  node.setId("serverMessage");
    	  }
          if(item.getType() == MessageType.SENT){
        	  node.setId("sentMessage");
    	  }
          if(item.getType() == MessageType.RECEIVED){
        	  node.setId("receivedMessage");
    	  }
    	node.setAlignment(Pos.CENTER_LEFT);
        node.getChildren().add(text);
        text.setWrappingWidth(420);
        setGraphic(node);
      }
    }
}
