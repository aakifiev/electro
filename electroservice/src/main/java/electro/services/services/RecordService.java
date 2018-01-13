package electro.services.services;

import electro.model.Record;
import electro.services.repository.RecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    private static final Logger log = LoggerFactory.getLogger(RecordService.class);

    public Long save(Long gardenPlotId, Long count) {
        Record newRecord = new Record(gardenPlotId, LocalDateTime.now(), count);
        recordRepository.save(newRecord);
        log.info("New record for garden plot with id: " + gardenPlotId + " saved.");
        return newRecord.getId();
    }

    public List<Record> getRecordsByGardenPlotId(Long gardenPlotId) {
        List<Record> records = recordRepository.getAllByGardenPlotId(gardenPlotId);
        log.info("Records for garden plot with id: " + gardenPlotId + " received.");
        return records;
    }
}
