package elctro;

import elctro.bot.ChatBotPollingBot;
import electro.model.client.GardenPlotClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

@SpringBootApplication
@Configuration
@EnableZuulProxy
public class ChatBot {
    public static void main(String[] args){
        SpringApplication.run(ChatBot.class, args);

    }

    @Bean
    public CommandLineRunner demo(){
        return (args -> {
            ApiContextInitializer.init();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            try {
                telegramBotsApi.registerBot(new ChatBotPollingBot());
            } catch (TelegramApiRequestException e) {
                e.printStackTrace();
            }
        });
    };

    @Bean
    public GardenPlotClient getGardenPlotClient(){
        return new GardenPlotClient();
    }
}
