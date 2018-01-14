package electro.services.controllers;

import electro.model.GardenPlot;
import electro.model.Record;
import electro.model.result.Result;
import electro.services.services.GardenPlotService;
import electro.services.services.RecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static electro.model.utils.GardenConstants.ADD_RECORD_FOR_GARDEN_PLOT_ID_URL;
import static electro.model.utils.GardenConstants.GET_GARDEN_PLOT_BY_ID_URL;
import static electro.model.utils.GardenConstants.GET_RECORDS_BY_GARDEN_PLOT_ID_URL;

@Controller
public class RecordController {
    private static Logger logger = LoggerFactory.getLogger(RecordController.class);

    @Autowired
    private RecordService recordService;

    @ResponseBody
    @RequestMapping(path = GET_RECORDS_BY_GARDEN_PLOT_ID_URL + "{id}", method = RequestMethod.GET)
    public Result<List<Record>> getAllRecordsByGardenPlotId(@PathVariable(value = "id") Long id) {
        logger.info("Get records for Garden Plot id: " + id);
        //GardenPlot gardenPlot = gardenPlotService.getGardenPlotById(id);
        //gardenPlot.getRecords();
        return Result.run(() -> recordService.getRecordsByGardenPlotId(id));
    }

    @ResponseBody
    @RequestMapping(path = ADD_RECORD_FOR_GARDEN_PLOT_ID_URL + "{id}/{count}", method = RequestMethod.GET)
    public Result<Long> addRecordForGardenPlotId(@PathVariable(value = "id") Long id,
                                                   @PathVariable(value = "count") Long count) {
        logger.info("Get records for Garden Plot id: " + id);
        //GardenPlot gardenPlot = gardenPlotService.getGardenPlotById(id);
        //gardenPlot.getRecords();
        return Result.success(recordService.addNewRecord(id, count));
    }
}
