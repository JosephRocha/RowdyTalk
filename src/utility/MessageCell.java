package utility;

import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class MessageCell extends ListCell<String> {
	@Override
    public void updateItem(String item, boolean empty) {
      super.updateItem(item, empty);
      HBox node = new HBox();
      Text text  = new Text(item);
      if (item != null) {
    	node.setAlignment(Pos.CENTER_LEFT);
        node.getChildren().add(text);
        text.setWrappingWidth(420);
        setGraphic(node);
      }else{
    	  setGraphic(null);
      }
    }
}
