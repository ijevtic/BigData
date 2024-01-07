package org.example.producers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.logging.Logger;

import static org.example.util.Constants.EARNINGS_TOPIC;

public class AlphaVantageProducerEarnings extends AlphaProducerAbstract{

    static Logger logger = Logger.getLogger(AlphaVantageProducerEarnings.class.getSimpleName());

    public static void main(String[] args) {

        KafkaProducer<String, String> producer = new KafkaProducer<>(getProducerProperties());

        try {
            String url = "https://www.alphavantage.co/query?function=EARNINGS&symbol=IBM&apikey=" + alphaVantageKey;
            String jsonString = fetchDataFromApi(url);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            System.out.println(jsonNode);
            JsonNode timeSeries = jsonNode.get("annualEarnings");
            if (timeSeries.isArray()) {
                ArrayNode arrayNode = (ArrayNode) timeSeries;

                for (JsonNode data : arrayNode) {
                    ProducerRecord<String, String> record = new ProducerRecord<>(EARNINGS_TOPIC, data.toString());
                    producer.send(record);
                    logger.info("Sending message: " + data);
                }
            } else {
                System.out.println("annualEarnings is not an array.");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        producer.close();
    }
}
