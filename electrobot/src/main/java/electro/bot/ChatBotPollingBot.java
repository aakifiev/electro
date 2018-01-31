package electro.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

@Component
public class ChatBotPollingBot extends TelegramLongPollingBot {

    @Autowired
    private ChatBotController chatBotController;

    public void onUpdateReceived(Update update) {
        if (update.hasMessage()){
            chatBotController.messageHandle(update);
        } else {
            chatBotController.callBackHandler(update);
        }

    }



    public String getBotUsername() {
        return "AakifievBot";
    }

    public String getBotToken() {
        return "468727535:AAHQ_lH46N01XQ5c-BYPWE0s6Aljyqek1_A";
    }



}
