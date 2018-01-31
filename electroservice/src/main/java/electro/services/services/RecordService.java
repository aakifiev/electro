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
    public Record addNewElectroCount(Record record) {
        log.info("searchrecord: ");
        Long gardenPlotId = record.getGardenPlotId();
        //TODO check last record on null
        Record lastRecord = recordRepository.findTopByGardenPlotIdAndPaymentOrderByDateDesc(gardenPlotId, false);
        log.info("lastrecord: " + lastRecord.toString());
        Record newRecord = new Record(gardenPlotId, new Date(), record.getCount(), false);
        recordRepository.save(newRecord);
        log.info("New record for garden plot with id: " + gardenPlotId + " added.");
        GardenPlot gardenPlotForUpdate = gardenPlotService.getGardenPlotById(gardenPlotId);
        gardenPlotForUpdate.setBill(gardenPlotForUpdate.getBill() + (newRecord.getCount() - lastRecord.getCount()) * 4);
        gardenPlotService.saveOrUpdate(gardenPlotForUpdate);
        return newRecord;
    }

    @Transactional
    public Record addNewElectroPayment(Record record) {
        log.info("searchrecord: ");
        Long gardenPlotId = record.getGardenPlotId();
        Record newRecord = new Record(gardenPlotId, new Date(), record.getCount(), true);
        recordRepository.save(newRecord);
        log.info("New record for garden plot with id: " + gardenPlotId + " added.");
        GardenPlot gardenPlotForUpdate = gardenPlotService.getGardenPlotById(gardenPlotId);
        gardenPlotForUpdate.setBill(gardenPlotForUpdate.getBill() - newRecord.getCount());
        gardenPlotService.saveOrUpdate(gardenPlotForUpdate);
        return newRecord;
    }

    public List<Record> getRecordsByGardenPlotId(Long gardenPlotId) {
        List<Record> records = recordRepository.getAllByGardenPlotId(gardenPlotId);
        log.info("Records for garden plot with id: " + gardenPlotId + " received.");
        return records;
    }
}
