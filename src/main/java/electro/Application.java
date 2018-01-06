package electro;

import electro.services.GardenPlotService;
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

    /*@Autowired
    private GardenPlotService gardenPlotService;*/

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]){
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(){
        return (args -> {

            //GardenPlot gardenPlot = gardenPlotService.getGardenPlotById(272L);
            //Hibernate.initialize(gardenPlot.getRecords());
            //System.out.println(gardenPlot.toString());

            //gardenPlotService.getGardenPlotById(272L);

            /*GardenPlot gardenPlot = new GardenPlot(272L, "Anton", "Akifev");
            Record record = new Record(gardenPlot, LocalDate.now(), 6415L);
            gardenPlot.setRecords(Arrays.asList(record));
            gardenPlotService.save(gardenPlot);*/
            // save a couple of customers
            /*repository.save(new Customer("Jack", "Bauer"));
            repository.save(new Customer("Chloe", "O'Brian"));
            repository.save(new Customer("Kim", "Bauer"));
            repository.save(new Customer("David", "Palmer"));
            repository.save(new Customer("Michelle", "Dessler"));*/

            // fetch all customers
            /*log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Customer customer : repository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            Customer customer = repository.findOne(1L);
            log.info("Customer found with findOne(1L):");
            log.info("--------------------------------");
            log.info(customer.toString());
            log.info("");

            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            for (Customer bauer : repository.findByLastName("Bauer")) {
                log.info(bauer.toString());
            }
            log.info("");*/
        });
    };
}
