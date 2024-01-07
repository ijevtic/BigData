package org.example.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Earnings {
    @JsonProperty("fiscalDateEnding")
    private String date;

    @JsonProperty("reportedEPS")
    private Double eps;

    public static Earnings fromJson(String json, ObjectMapper objectMapper) throws IOException {
        return objectMapper.readValue(json, Earnings.class);
    }
}