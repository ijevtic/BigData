package org.example.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoData {
    @JsonProperty("1b. open (USD)")
    private String openUsd;

    @JsonProperty("2b. high (USD)")
    private String highUsd;

    @JsonProperty("3b. low (USD)")
    private String lowUsd;

    @JsonProperty("4b. close (USD)")
    private String closeUsd;

    @JsonProperty("5. volume")
    private String volume;

    @JsonProperty("6. market cap (USD)")
    private String marketCapUsd;

    private String date;

    public static CryptoData fromJson(String json, ObjectMapper objectMapper) throws IOException {
        return objectMapper.readValue(json, CryptoData.class);
    }
    @Override
    public String toString() {
        return "CryptoData{" +
                "openUsd='" + openUsd + '\'' +
                ", highUsd='" + highUsd + '\'' +
                ", lowUsd='" + lowUsd + '\'' +
                ", closeUsd='" + closeUsd + '\'' +
                ", volume='" + volume + '\'' +
                ", marketCapUsd='" + marketCapUsd + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}