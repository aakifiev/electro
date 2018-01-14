package electro.services.services;

import electro.model.GardenPlot;
import electro.model.Record;
import electro.services.repository.RecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private GardenPlotService gardenPlotService;

    private static final Logger log = LoggerFactory.getLogger(RecordService.class);

    @Transactional
    public Long addNewRecord(Long gardenPlotId, Long count) {
        log.info("searchrecord: ");
        Record lastRecord = recordRepository.findTopByGardenPlotIdOrderByDateDesc(gardenPlotId);
        log.info("lastrecord: " + lastRecord.toString());
        Record newRecord = new Record(gardenPlotId, new Date(), count);
        recordRepository.save(newRecord);
        log.info("New record for garden plot with id: " + gardenPlotId + " added.");
        GardenPlot gardenPlotForUpdate = gardenPlotService.getGardenPlotById(gardenPlotId);
        gardenPlotForUpdate.setBill(gardenPlotForUpdate.getBill() + (newRecord.getCount() - lastRecord.getCount()) * 4);
        gardenPlotService.saveOrUpdate(gardenPlotForUpdate);
        return newRecord.getId();
    }

    public List<Record> getRecordsByGardenPlotId(Long gardenPlotId) {
        List<Record> records = recordRepository.getAllByGardenPlotId(gardenPlotId);
        log.info("Records for garden plot with id: " + gardenPlotId + " received.");
        return records;
    }
}
