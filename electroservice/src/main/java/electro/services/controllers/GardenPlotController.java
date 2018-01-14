package electro.services.controllers;

import electro.model.GardenPlot;
import electro.model.result.Result;
import electro.model.utils.GardenConstants;
import electro.services.services.GardenPlotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static electro.model.utils.GardenConstants.GET_GARDEN_PLOT_BY_ID_URL;
import static electro.model.utils.GardenConstants.GET_GARDEN_PLOT_FULL_BY_ID_URL;

@Controller
public class GardenPlotController {

    private static Logger logger = LoggerFactory.getLogger(GardenPlotController.class);

    //@Autowired
    //private GardenPlotRepository gardenPlotRepository;

    @Autowired
    private GardenPlotService gardenPlotService;

    @ResponseBody
    @RequestMapping(path = GET_GARDEN_PLOT_BY_ID_URL + "{id}", method = RequestMethod.GET)
    public Result<GardenPlot> getGardenPlotById(@PathVariable(value = "id") Long id) {
        logger.info("Get Garden Plot for id: " + id);
        //GardenPlot gardenPlot = gardenPlotService.getGardenPlotById(id);
        //gardenPlot.getRecords();
        return Result.run(() -> gardenPlotService.getGardenPlotById(id));
    }

    @ResponseBody
    @RequestMapping(path = GET_GARDEN_PLOT_FULL_BY_ID_URL + "{id}")
    public Result<GardenPlot> getGardenPlotByIdWithRecords(@PathVariable(value = "id") Long id) {
        logger.info("Get Garden Plot for id with records: " + id);
        GardenPlot gardenPlot = gardenPlotService.getGardenPlotById(id);
        //gardenPlot.getRecords();
        return Result.success(gardenPlot);
    }
}
