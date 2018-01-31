package electro.bot;

import electro.bot.util.Constants;
import electro.model.GardenPlot;
import electro.model.Record;
import electro.model.client.GardenPlotClient;
import electro.model.client.RecordClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component()
public class ChatBotController {

    private static final Pattern regexForOrderNumber = Pattern.compile("Номер участка: (-?\\d+)");

    @Autowired
    private ChatBotPollingBot chatBotPollingBot;

    @Autowired
    private GardenPlotClient gardenPlotClient;

    @Autowired
    private RecordClient recordClient;

    private Map<Integer, Map<String, Object>> parametersMap = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(ChatBotController.class);

    public void messageHandle(Update update) {
        //GardenPlotClient gardenPlotClient = new GardenPlotClient();
        SendMessage sendMessage;
        String currentMenu = Optional.ofNullable((String) getParameterFromParametersMap(update.getMessage().getFrom().getId(),
                Constants.PARAMETER_CURRENT_MENU)).orElse("");
        switch (currentMenu) {
            case Constants.MENU_MAIN:
                sendMessage = handleMainMenu(update);
                break;
            case Constants.MENU_GET_GARDEN_PLOT:
                sendMessage = handleGetGardenPlotInfo(update);
                break;
            case Constants.MENU_SET_COUNT:
                sendMessage = setCountHandler(update);
                break;
            default:
                sendMessage = getMainMenu(update.getMessage().getChatId(), update.getMessage().getFrom().getId());
        }
        try {
            chatBotPollingBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage setCountHandler(Update update) {
        CallbackQuery currentCallback = (CallbackQuery) getParameterFromParametersMap(update.getMessage().getFrom().getId(),
                Constants.PARAMETER_CURRENT_CALLBACK);
        Long gardenPlotId = getGardenPlotIdFromCallBack(currentCallback);
        if (gardenPlotId != null && !Constants.BUTTON_CANCELLATION.equals(update.getMessage().getText())) {
            try {
                switch (currentCallback.getData()) {
                    case Constants.CALLBACK_COMMAND_COUNTER:
                        Long electroCount = Long.valueOf(update.getMessage().getText());
                        recordClient.setNewRecord(new Record(gardenPlotId, new Date(), electroCount, false));
                        break;
                    case Constants.CALLBACK_COMMAND_PAY:
                        Long electroPayment = Long.valueOf(update.getMessage().getText());
                        recordClient.setNewRecord(new Record(gardenPlotId, new Date(), electroPayment, true));
                        break;
                    default:
                }
                updateGardenPlotInfo(currentCallback.getMessage().getChatId(), currentCallback.getMessage().getMessageId(),
                        gardenPlotId);
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage());
                AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
                answerCallbackQuery.setCallbackQueryId(currentCallback.getId());
                answerCallbackQuery.setText("Команда " + currentCallback.getData() + " не выполнена");
                try {
                    chatBotPollingBot.execute(answerCallbackQuery);
                } catch (TelegramApiException e1) {
                    logger.error(e1.getLocalizedMessage());
                }
            }
        }
        //updateGardenPlotInfo(currentCallback.getMessage().getChatId(), currentCallback.getMessage().getMessageId(), 272L);
        removeParametersFromParametersMapById(update.getMessage().getFrom().getId());
        return getMainMenu(update.getMessage().getChatId(), update.getMessage().getFrom().getId());
    }

    private void updateGardenPlotInfo(Long chatId, Integer messageId, Long gardenPlotId) {
        GardenPlot gardenPlot = gardenPlotClient.get(gardenPlotId);
        try {
            DeleteMessage deleteMessage = new DeleteMessage(chatId, messageId);
            chatBotPollingBot.execute(deleteMessage);
            chatBotPollingBot.execute(generateNewMessage(chatId, getGardenPlotInfo(gardenPlot))
                    .setReplyMarkup(getReplyKeyboardMarkup()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void callBackHandler(Update update) {
        logger.info("Start callback handler");
        SendMessage sendMessage;
        switch (update.getCallbackQuery().getData()) {
            case Constants.CALLBACK_COMMAND_COUNTER:
                addNewParameterToParametersMap(update.getCallbackQuery().getFrom().getId(),
                        Constants.PARAMETER_CURRENT_MENU, Constants.MENU_SET_COUNT);
                addNewParameterToParametersMap(update.getCallbackQuery().getFrom().getId(),
                        Constants.PARAMETER_CURRENT_CALLBACK, update.getCallbackQuery());
                sendMessage = createMenuForCounter(update);
                break;
            case Constants.CALLBACK_COMMAND_PAY:
                addNewParameterToParametersMap(update.getCallbackQuery().getFrom().getId(),
                        Constants.PARAMETER_CURRENT_MENU, Constants.MENU_SET_COUNT);
                addNewParameterToParametersMap(update.getCallbackQuery().getFrom().getId(),
                        Constants.PARAMETER_CURRENT_CALLBACK, update.getCallbackQuery());
                sendMessage = createMenuForCounter(update);
                break;

            default:
                sendMessage = new SendMessage(update.getCallbackQuery().getMessage().getChatId(),
                        "eee");
        }

        try {
            chatBotPollingBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage createMenuForCounter(Update update) {
        return generateNewMessage(update.getCallbackQuery().getMessage().getChatId(),
                "Введите показания счетчика").setReplyMarkup(getCancelAcceptMenu());
    }

    private ReplyKeyboardMarkup getCancelAcceptMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(Constants.BUTTON_CANCELLATION);
        keyboard.add(firstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    private SendMessage createGardenPlotMessage(String text) {
        SendMessage newMessage = new SendMessage();
        newMessage.setText(text);
        newMessage.enableMarkdown(true);
        newMessage.setReplyMarkup(getReplyKeyboardMarkup());
        return newMessage;
    }

    private InlineKeyboardMarkup getReplyKeyboardMarkup() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(new InlineKeyboardButton().setText("счетчик").setCallbackData(Constants.CALLBACK_COMMAND_COUNTER));
        firstRow.add(new InlineKeyboardButton().setText("оплатить").setCallbackData(Constants.CALLBACK_COMMAND_PAY));
        rowsInline.add(firstRow);
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(new InlineKeyboardButton().setText("все показания").setCallbackData("все показания"));
        secondRow.add(new InlineKeyboardButton().setText("счет").setCallbackData(Constants.CALLBACK_COMMAND_BILL));
        rowsInline.add(secondRow);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    private String getGardenPlotInfo(GardenPlot gardenPlot) {
        StringBuilder gardenInfo = new StringBuilder();
        gardenInfo.append("*Номер участка:* ").append(gardenPlot.getId()).append("\n");
        gardenInfo.append("_Имя:_ ").append(gardenPlot.getFirstName()).append("\n");
        gardenInfo.append("*Фамилия:* ").append(gardenPlot.getLastName()).append("\n");
        gardenInfo.append("*Долг/Переплата:* ").append(gardenPlot.getBill()).append("\n");
        return gardenInfo.toString();
    }

    private SendMessage handleMainMenu(Update update) {
        logger.info("Handle main menu");
        addNewParameterToParametersMap(update.getMessage().getFrom().getId(),
                Constants.PARAMETER_CURRENT_MENU,
                Constants.MENU_GET_GARDEN_PLOT);
        return generateNewMessage(update.getMessage().getChatId(), "Введите номер участка").setReplyMarkup(getCancelAcceptMenu());
    }

    private SendMessage handleGetGardenPlotInfo(Update update) {
        logger.info("Handle get garden plot info");
        if (!Constants.BUTTON_CANCELLATION.equals(update.getMessage().getText())) {
            Long gardenPlotId = Long.valueOf(update.getMessage().getText());
            GardenPlot gardenPlot = gardenPlotClient.get(gardenPlotId);
            try {
                chatBotPollingBot.execute(generateNewMessage(update.getMessage().getChatId(), getGardenPlotInfo(gardenPlot))
                        .setReplyMarkup(getReplyKeyboardMarkup()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        return getMainMenu(update.getMessage().getChatId(), update.getMessage().getFrom().getId());
    }

    private SendMessage getMainMenu(Long chatId, Integer tUserId) {
        addNewParameterToParametersMap(tUserId,
                Constants.PARAMETER_CURRENT_MENU,
                Constants.MENU_MAIN);
        return generateNewMessage(chatId, "main menu").setReplyMarkup(getKeyboardForMainMenu());
    }

    private ReplyKeyboardMarkup getKeyboardForMainMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add("Получить инфо об участке");
        keyboard.add(firstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    private SendMessage generateNewMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage(
                chatId, text
        );
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    // methods for working with a temporary cache
    private void addNewParameterToParametersMap(Integer userId, String name, Object object) {
        Map<String, Object> values = parametersMap.get(userId);
        if (values == null) {
            values = new HashMap<>();
            parametersMap.put(userId, values);
        }
        values.put(name, object);
    }

    private Object getParameterFromParametersMap(Integer userId, String name) {
        Map<String, Object> values = parametersMap.get(userId);
        if (values == null) {
            return null;
        } else {
            return values.get(name);
        }
    }

    private void removeParametersFromParametersMapById(Integer userId) {
        parametersMap.remove(userId);
    }

    private Long getGardenPlotIdFromCallBack(CallbackQuery callbackQuery) {
        Matcher regexMatcher;
        if ((regexMatcher = regexForOrderNumber.matcher(callbackQuery.getMessage().getText())).find()) {
            return Long.valueOf(regexMatcher.group(1));
        }
        return null;
    }
}
