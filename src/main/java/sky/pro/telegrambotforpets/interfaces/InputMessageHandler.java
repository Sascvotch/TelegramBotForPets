package sky.pro.telegrambotforpets.interfaces;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import sky.pro.telegrambotforpets.constants.BotState;

public interface InputMessageHandler {
    SendMessage handle(Message message);

    BotState getHandlerName();
}
