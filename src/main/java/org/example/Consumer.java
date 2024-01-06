package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import static org.example.CsvWriter.header;


public class Consumer {
    private static final Logger logger = Logger.getLogger(Consumer.class.getSimpleName());
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String CSV_FILE_PATH = "/home/ijevtic/Desktop/wiki.csv";

    public static void main(String[] args) throws IOException {
        logger.info("Kafka Simple Consumer");

        String groupId = "my-group";
        String topic = "wikitopic";
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        consumer.subscribe(List.of(topic));

        List<WikiEntry> wikiEntries = new ArrayList<>();



        int i = 0;
        while (i < 3000) {
            i++;
            logger.info("Polling...");
            consumer.poll(100).forEach(record -> {
                try {
                    WikiEntry w = WikiEntry.fromJson(record.value(), objectMapper);
                    logger.info(w.toString());
                    wikiEntries.add(w);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
            // Write the header
            writer.write(String.join(",", header));
            writer.newLine();

            // Write each WikiEntry to the CSV file
            for (WikiEntry entry : wikiEntries) {
                String[] data = CsvWriter.getDataFromWikiEntry(entry);
                if(data == null) continue;

                writer.write(String.join(",", data));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
