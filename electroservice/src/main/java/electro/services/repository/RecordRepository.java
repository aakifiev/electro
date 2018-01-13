package electro.services.repository;

import electro.model.Record;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecordRepository extends CrudRepository<Record, Long> {

    public List<Record> getAllByGardenPlotId(Long gardenPlotId);
}
