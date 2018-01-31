package electro.bot.configuration;

import electro.bot.ChatBotPollingBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class BotConfiguration {

    @Autowired
    private ChatBotPollingBot chatBotPollingBot;

    private DefaultBotSession ordersBotSession;

    @PostConstruct
    public void start() {
        try {
            chatBotPollingBot.clearWebhook();
            ordersBotSession = ApiContext.getInstance(DefaultBotSession.class);
            ordersBotSession.setToken(chatBotPollingBot.getBotToken());
            ordersBotSession.setOptions(chatBotPollingBot.getOptions());
            ordersBotSession.setCallback(chatBotPollingBot);
            ordersBotSession.start();
        } catch (TelegramApiException e) {
            BotLogger.error("Initialize", e);
        }
    }

    @PreDestroy
    public void stop() {
        if (ordersBotSession != null) {
            ordersBotSession.stop();
        }
    }
}
