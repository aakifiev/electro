package electro.model.result;

import electro.model.Record;

public class RecordResult extends Result<Record> {

    public RecordResult() {
        super();
    }

    public RecordResult(String message, Record data) {
        super(message, data);
    }
}
