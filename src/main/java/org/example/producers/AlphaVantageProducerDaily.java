package org.example.producers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.logging.Logger;

import static org.example.util.Constants.*;

public class AlphaVantageProducerDaily extends AlphaProducerAbstract {

    static Logger logger = Logger.getLogger(AlphaVantageProducerMonthly.class.getSimpleName());

    public static void main(String[] args) {
        KafkaProducer<String, String> producer = new KafkaProducer<>(getProducerProperties());

        try {
            String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&" +
                    "outputsize=full&symbol=" + SYMBOL +"&apikey=" + alphaVantageKey;
            String jsonString = fetchDataFromApi(url);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            JsonNode timeSeries = jsonNode.get("Time Series (Daily)");

            timeSeries.fields().forEachRemaining(entry -> {
                String date = entry.getKey();
                JsonNode data = entry.getValue();

                String messageValue = data.toString();

                ProducerRecord<String, String> record = new ProducerRecord<>(DAILY_TOPIC, date, messageValue);
                producer.send(record);
                logger.info("Sending message: " + date);
                logger.info("Sending message: " + messageValue);

            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        producer.close();
    }
}
