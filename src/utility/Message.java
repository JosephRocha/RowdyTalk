package utility;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class Message implements Serializable {

	private String message_content;
	private String origin_user;
	public ArrayList<String> user_list = new ArrayList<String>();
	private MessageType type;
	public boolean isImage;
	private byte[] imageBytes;
	
	public void setMessage(String message){
		this.message_content = message;
	}
	
	public void setOrigin(String origin_user){
		this.origin_user = origin_user;
	}
	
	public void setUsers(ArrayList<String> users){
		user_list.addAll(users);
	}
	
	public String getMessage(){
		return message_content;
	}
	
	public String getOrigin(){
		return origin_user;
	}
	
	public ArrayList<String> getUsers(){
		return user_list;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public Image getImage() {
		try {
			BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
			Image image = SwingFXUtils.toFXImage(img, null);
			return image;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setImage(Image image) {
		try {
			BufferedImage bimage = SwingFXUtils.fromFXImage(image, null);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bimage, "png", baos);
			baos.flush();
			this.imageBytes = baos.toByteArray();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isImage() {
		return isImage;
	}

	public void setIsImage(boolean isImage) {
		this.isImage = isImage;
	}
}
