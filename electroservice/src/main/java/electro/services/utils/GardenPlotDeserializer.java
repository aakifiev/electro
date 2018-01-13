package electro.services.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import electro.model.GardenPlot;

import java.io.IOException;
import java.io.StringWriter;

public class GardenPlotDeserializer extends JsonSerializer<GardenPlot> {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void serialize(GardenPlot gardenPlot, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, gardenPlot);
        jsonGenerator.writeFieldName(writer.toString());
    }

    /*protected GardenPlotDeserializer(Class<?> vc) {
        super(vc);
    }

    protected GardenPlotDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected GardenPlotDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    @Override
    public GardenPlot deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        Long id = (Long) (((IntNode) jsonNode.get("id")).numberValue());
        String firstName = jsonNode.get("firstName").asText();
        String lastName = jsonNode.get("lastName").asText();
        return new GardenPlot(id, firstName, lastName);
    }*/
}
