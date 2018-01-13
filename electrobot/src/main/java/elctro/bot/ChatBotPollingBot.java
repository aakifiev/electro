package elctro.bot;

import electro.model.GardenPlot;
import electro.model.client.GardenPlotClient;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class ChatBotPollingBot extends TelegramLongPollingBot {

    /*@Autowired
    private GardenPlotClient gardenPlotClient;*/

    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            //System.out.println(messageText);
            Long chatId = update.getMessage().getChatId();
            //System.out.println(chatId);

            GardenPlotClient gardenPlotClient = new GardenPlotClient();

            GardenPlot gardenPlot = gardenPlotClient.get(Long.valueOf(messageText));
            System.out.println(gardenPlot);
            /*List<PhotoSize> photo = update.getMessage().getPhoto();
            SendPhoto sendPhoto = PhotoUtil.getSendPhoto(photo, chatId);*/
            SendMessage sendMessage = new SendMessage().setChatId(chatId).setText(gardenPlot.toString());
            try {
                //sendPhoto(sendPhoto);
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public String getBotUsername() {
        return "AakifievBot";
    }

    public String getBotToken() {
        return "468727535:AAHQ_lH46N01XQ5c-BYPWE0s6Aljyqek1_A";
    }



}
