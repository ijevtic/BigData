package org.example.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockDataDaily {
    @JsonProperty("1. open")
    private String open;

    @JsonProperty("2. high")
    private String high;

    @JsonProperty("3. low")
    private String low;

    @JsonProperty("4. close")
    private String close;

    @JsonProperty("6. volume")
    private String volume;

    private String date;

    public static StockDataDaily fromJson(String json, ObjectMapper objectMapper) throws IOException {
        return objectMapper.readValue(json, StockDataDaily.class);
    }
}