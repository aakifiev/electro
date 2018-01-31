package electro;

import electro.model.GardenPlot;
import electro.model.Record;
import electro.services.repository.RecordRepository;
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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Date;

@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
public class Application {

    @Autowired
    private GardenPlotService gardenPlotService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private RecordRepository recordRepository;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo() {
        return (args -> {
            GardenPlot gardenPlot = new GardenPlot(272L, "Anton", "Akifev");
            gardenPlot.setBill(1100L);
            gardenPlotService.saveOrUpdate(gardenPlot);
            recordRepository.save(new Record(gardenPlot.getId(), new Date(), 0L, Boolean.FALSE));
            /*Long id = recordService.addNewRecord(gardenPlot.getId(), 6415L);
            GardenPlot gardenPlotById = gardenPlotService.getGardenPlotById(272L);
            System.out.println(gardenPlotById);*/
        });
    }

    ;
}
