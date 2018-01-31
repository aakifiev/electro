package electro;

import electro.model.client.GardenPlotClient;
import electro.model.client.RecordClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
public class ChatBot {

    public static void main(String[] args){
        SpringApplication.run(ChatBot.class, args);
        /*ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new ChatBotPollingBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }*/

    }

    @Bean
    public CommandLineRunner demo(){
        return (args -> {
            /*ApiContextInitializer.init();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            try {
                telegramBotsApi.registerBot(new ChatBotPollingBot());
            } catch (TelegramApiRequestException e) {
                e.printStackTrace();
            }*/
        });
    };

    /*@Bean
    public GardenPlotClient getGardenPlotClient(){
        return new GardenPlotClient();
    }*/
}
