package electro.model.client;

import electro.model.GardenPlot;
import electro.model.result.GardenPlotResult;

public class GardenPlotClient extends Client {

    private static final String SERVICE_PATH = "/electro/garden/";

    public GardenPlotClient() {
        super(SERVICE_PATH);
    }

    public GardenPlot get(Long id) {
        return get("gardenPlot/" + id.toString(), GardenPlotResult.class).getData();
    }

    public GardenPlot getWithRecords(Long id) {
        return get("gardenPlotFull/" + id.toString(), GardenPlotResult.class).getData();
    }
}
