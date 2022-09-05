package sky.pro.telegrambotforpets.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sky.pro.telegrambotforpets.constants.BotState;
import sky.pro.telegrambotforpets.interfaces.GuestService;
import sky.pro.telegrambotforpets.model.BotStateContext;
import sky.pro.telegrambotforpets.model.MyWizardTelegramBot;
import sky.pro.telegrambotforpets.services.GuestServiceImpl;
import org.springframework.context.annotation.Lazy;
import sky.pro.telegrambotforpets.services.MainMenuService;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.util.List;

import static sky.pro.telegrambotforpets.constants.Constants.*;

/**
 * отвечает за работу телеграм бота
 */
@Service
public class TelegramBotUpdatesListener {

    @Autowired
    private TelegramBot telegramBot;

    private final GuestService guestService;
    private MyWizardTelegramBot myWizardTelegamBot;
    private MainMenuService mainMenuService;

    private BotStateContext botStateContext;


    public TelegramBotUpdatesListener(GuestServiceImpl guestService, @Lazy MyWizardTelegramBot myWizardTelegamBot,
                                      MainMenuService mainMenuService, BotStateContext botStateContext) {
        this.guestService = guestService;
        this.myWizardTelegamBot = myWizardTelegamBot;
        this.mainMenuService = mainMenuService;
        this.botStateContext = botStateContext;
    }

    public Object handleUpdate(Update update) {
        SendMessage replyMessage = null;

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            //log.info("New callbackQuery from User: {}, userId: {}, with data: {}", update.getCallbackQuery().getFrom().getUserName(),
            //        callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
            return processCallbackQuery(callbackQuery);
        }


        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            // log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
            //         message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }

        return replyMessage;
    }


    private SendMessage handleInputMessage(Message message) {
        String inputMsg = message.getText();
        Long userId = message.getFrom().getId();
        long chatId = message.getChatId();
        BotState botState = BotState.FILLING_PROFILE;
        SendMessage replyMessage;

/*
        if (!guestService.doesGuestAlreadyExistsInDB(update)) {
            guestService.saveGuestToDB(update);
        }
*/

        switch (inputMsg) {
            case "/start":
                //botState = BotState.ASK_DESTINY;
                try {
                    myWizardTelegamBot.sendPhoto(chatId, "imageCaption", "c:\\Users\\Karpukhin-EV\\IdeaProjects\\Курс_7\\TelegramBotForPets\\yandex.png");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                break;
            case MENU_1_BUTTON_1:
                botState = BotState.FILLING_PROFILE;
                break;
            case MENU_1_BUTTON_2:
                botState = BotState.SHOW_USER_PROFILE;
                break;
            case MENU_1_BUTTON_3:
                try {
                    myWizardTelegamBot.sendDocument(chatId, "Ваша анкета", "c:\\Users\\Karpukhin-EV\\IdeaProjects\\Курс_7\\TelegramBotForPets\\yandex.png");
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                botState = BotState.SHOW_USER_PROFILE;
                break;
            case MENU_1_BUTTON_4:
                botState = BotState.SHOW_HELP_MENU;
                break;
            default:
                botState = BotState.SHOW_HELP_MENU; //userDataCache.getUsersCurrentBotState(userId);
                break;
        }

        //userDataCache.setUsersCurrentBotState(userId, botState);

        replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }


    private BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        final long chatId = buttonQuery.getMessage().getChatId();
        final long userId = buttonQuery.getFrom().getId();

        BotApiMethod<?> callBackAnswer = mainMenuService.getMainMenuMessage(chatId, "Воспользуйтесь главным меню");

        //From Destiny choose buttons
        if (buttonQuery.getData().equals("buttonYes")) {
            callBackAnswer = new SendMessage(buttonQuery.getId(), "Как тебя зовут ?");
            //userDataCache.setUsersCurrentBotState(userId, BotState.ASK_AGE);
        } else if (buttonQuery.getData().equals("buttonNo")) {
            callBackAnswer = sendAnswerCallbackQuery("Возвращайся, когда будешь готов", false, buttonQuery);
        } else if (buttonQuery.getData().equals("buttonIwillThink")) {
            callBackAnswer = sendAnswerCallbackQuery("Данная кнопка не поддерживается", true, buttonQuery);
        }

        //From Gender choose buttons
        else if (buttonQuery.getData().equals("buttonMan")) {
/*
            UserProfileData userProfileData = userDataCache.getUserProfileData(userId);
            userProfileData.setGender("М");
            userDataCache.saveUserProfileData(userId, userProfileData);
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_COLOR);
*/
            callBackAnswer = new SendMessage(buttonQuery.getId(), "Твоя любимая цифра");
        } else if (buttonQuery.getData().equals("buttonWoman")) {
/*
            UserProfileData userProfileData = userDataCache.getUserProfileData(userId);
            userProfileData.setGender("Ж");
            userDataCache.saveUserProfileData(userId, userProfileData);
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_COLOR);
*/
            callBackAnswer = new SendMessage(buttonQuery.getId(), "Твоя любимая цифра");

        } else {
            //userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
        }

        return callBackAnswer;
    }


    private AnswerCallbackQuery sendAnswerCallbackQuery(String text, boolean alert, CallbackQuery callbackquery) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackquery.getId());
        answerCallbackQuery.setShowAlert(alert);
        answerCallbackQuery.setText(text);
        return answerCallbackQuery;
    }


/*
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {

            if (!guestService.doesGuestAlreadyExistsInDB(update)) {
                guestService.saveGuestToDB(update);
            }
            switch (update.getMessage().getText()) {
                case MENU_1_BUTTON_1:
                    //Здесь будет ответ на запрос О приюте
                    sendMsg(update, "Информация о приюте");
                    long shelter_id = 1;
                    String shelter_name = "\"Тяпа\"";
                    String shelter_adress = "г.Саратов, ул. Революции, дом 50";
                    String shelter_mappach = "Картинка!";
                    String shelter_recomendationpach = "Возьмите с собой еду для питовцев";
                    String shelter_schedule = "Время работы с 09:00 до 20:00, ежедневно";
                    String shelter_specification = "Собаки, ценки";
                    String shelter_description = "Приют основан в 2022 г. командой 3Джуна !!!";
                    sendMsg(update, shelter_name + " " + shelter_adress + "\n");
                    sendMsg(update, shelter_mappach + " " + shelter_recomendationpach + "\n");

                    break;
                case MENU_1_BUTTON_2:
                    //Здесь будет ответ на запрос Как взять питомца
                    sendMsg(update, "Как взять питомца из приюта");
                    break;
                case MENU_1_BUTTON_3:
                    //Здесь будет Отправка отчета
                    sendMsg(update, "Отправить отчет о питомце");
                    break;
                case MENU_1_BUTTON_4:
                    //Здесь будем звать волонтера
                    sendMsg(update, "Позвать волонтера");
                    break;
                default:
                    //В ответ на неопознанную команду выдает меню
                    sendMenu(update.getMessage().getChatId());
                    break;
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
*/

/*

    public void sendMsg(Update update, String text) {
        Long chatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage(chatId, text);
        SendResponse responce = telegramBot.execute(message);
        System.out.println(responce.isOk());
        System.out.println(responce.errorCode());

    }

    private void sendMenu(long chatId) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{MENU_1_BUTTON_1, MENU_1_BUTTON_2},
                new String[]{MENU_1_BUTTON_3, MENU_1_BUTTON_4})
                .oneTimeKeyboard(true)
                .resizeKeyboard(true)
                .selective(true);

        SendMessage request = new SendMessage(chatId, "Выберите пункт меню")
                .replyMarkup(replyKeyboardMarkup)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);

        SendResponse sendResponse = telegramBot.execute(request);
        if (!sendResponse.isOk()) {
            int codeError = sendResponse.errorCode();
            String description = sendResponse.description();
        }
    }
*/


}