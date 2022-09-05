package sky.pro.telegrambotforpets.model;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sky.pro.telegrambotforpets.listener.TelegramBotUpdatesListener;

import java.io.File;
import java.io.FileNotFoundException;

@Component
public class MyWizardTelegramBot extends TelegramWebhookBot {

    private String webHookPath;
    private String botUserName;
    private String botToken;

    private TelegramBotUpdatesListener telegramFacade;

    public MyWizardTelegramBot(TelegramBotUpdatesListener telegramBotUpdatesListener) {
        this.telegramFacade = telegramFacade;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        //return null;
        final BotApiMethod<?> replyMessageToUser = (BotApiMethod<?>) telegramFacade.handleUpdate(update);

        return replyMessageToUser;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }

    public void sendPhoto(long chatId, String imageCaption, String imagePath) throws FileNotFoundException, TelegramApiException {
        File image = ResourceUtils.getFile(imagePath);
        SendPhoto sendPhoto = new SendPhoto();
        InputFile fileToSend = new InputFile(image);
        sendPhoto.setPhoto(fileToSend);
        sendPhoto.setChatId(chatId);
        sendPhoto.setCaption(imageCaption);
        execute(sendPhoto);
    }

    public void sendDocument(long chatId, String caption, String sendFile) throws TelegramApiException {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId);
        sendDocument.setCaption(caption);
        InputFile fileToSend = new InputFile(sendFile);
        sendDocument.setDocument(fileToSend);
        execute(sendDocument);
    }

}