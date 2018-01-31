package electro.model.client;

import electro.model.Record;
import electro.model.result.RecordResult;
import org.springframework.stereotype.Component;

import static electro.model.utils.GardenConstants.*;

@Component
public class RecordClient extends Client {

    private static final String SERVICE_PATH = "/electro";

    public RecordClient() {
        super(SERVICE_PATH);
    }

    public Record setNewRecord(final Record record){
        return post(ADD_RECORD_FOR_GARDEN_PLOT_ID_URL, record, RecordResult.class).getData();
    }
}
