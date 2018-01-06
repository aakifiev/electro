package electro.services;

import electro.model.GardenPlot;
import electro.repository.GardenPlotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class GardenPlotService {

    private static final Logger log = LoggerFactory.getLogger(GardenPlot.class);

    @Autowired
    private GardenPlotRepository gardenPlotRepository;

    @Cacheable({"gardenPlot", "id"})
    public GardenPlot getGardenPlotById(Long id){
        log.info("Get garden plot from data base");
        return gardenPlotRepository.findOne(id);
    }
}
