package electro;

import electro.model.GardenPlot;
import electro.services.services.GardenPlotService;
import electro.services.services.RecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class Application {

    @Autowired
    private GardenPlotService gardenPlotService;

    @Autowired
    private RecordService recordService;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo() {
        return (args -> {
            GardenPlot gardenPlot = new GardenPlot(272L, "Anton", "Akifev");
            gardenPlotService.addNewGardenPlot(gardenPlot);
            Long id = recordService.save(gardenPlot.getId(), 6415L);
            log.info("record id: " + id);
            log.info("record" + recordService.getRecordsByGardenPlotId(272L));
        });
    }

    ;
}
