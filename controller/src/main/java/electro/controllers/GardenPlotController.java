package electro.controllers;

import electro.model.GardenPlot;
import electro.services.GardenPlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/garden")
public class GardenPlotController {

    //@Autowired
    //private GardenPlotRepository gardenPlotRepository;

    @Autowired
    private GardenPlotService gardenPlotService;

    @GetMapping(path = "/getGardenPlot")
    @ResponseBody
    public GardenPlot getGardenPlotById(@RequestParam (value="id", defaultValue = "null") Long id){
        GardenPlot gardenPlot = gardenPlotService.getGardenPlotById(id);
        gardenPlot.getRecords();
        return gardenPlot;
    }

}
