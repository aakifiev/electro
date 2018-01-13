package electro.model.result;

import electro.model.GardenPlot;

public class GardenPlotResult extends Result<GardenPlot> {
    public GardenPlotResult() {
        super();
    }

    public GardenPlotResult(String message, GardenPlot data) {
        super(message, data);
    }
}
