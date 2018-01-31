package electro.model.client;

import electro.model.GardenPlot;
import electro.model.result.GardenPlotResult;
import org.springframework.stereotype.Component;

import static electro.model.utils.GardenConstants.*;

@Component
public class GardenPlotClient extends Client {

    private static final String SERVICE_PATH = "/electro";

    public GardenPlotClient() {
        super(SERVICE_PATH);
    }

    public GardenPlot get(Long id) {
        return get(GET_GARDEN_PLOT_BY_ID_URL + id.toString(), GardenPlotResult.class).getData();
    }

    public GardenPlot getWithRecords(Long id) {
        return get(GET_GARDEN_PLOT_FULL_BY_ID_URL + id.toString(), GardenPlotResult.class).getData();
    }
}
