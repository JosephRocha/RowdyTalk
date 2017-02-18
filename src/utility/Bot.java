package utility;

import java.io.IOException;

import com.michaelwflaherty.cleverbotapi.CleverBotQuery;

public class Bot {
	public Message getReply(String request){
		Message message = new Message();
		CleverBotQuery bot = new CleverBotQuery("7a9d7fac47726c65874ee2f60526eba8", request);
		try
		{
			bot.sendRequest();
			message.setMessage(bot.getResponse());
			message.setOrigin("Watson");
			message.setType(MessageType.BOT);
		}
		catch (IOException e)
		{
			message.setOrigin("ERROR");
			message.setMessage("CANNOT CONNECT");
		}
		return message;
	}

}
