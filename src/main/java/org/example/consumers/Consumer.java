package org.example.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.util.CsvWriter;
import org.example.types.WikiEntry;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import static org.example.util.Constants.WIKI_TOPIC;


public class Consumer extends ConsumerAbstract{
    private static final Logger logger = Logger.getLogger(Consumer.class.getSimpleName());

    private static final String CSV_FILE_PATH = "/home/ijevtic/Desktop/wiki.csv";

    public static void main(String[] args) throws IOException {
        logger.info("Kafka Simple Consumer");

        String groupId = "my-group";

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getConsumerProperties(groupId));

        consumer.subscribe(List.of(WIKI_TOPIC));

        List<WikiEntry> wikiEntries = new ArrayList<>();

        int i = 0;
        while (i < 500) {
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
    public static String[] header = {
            "$schema",
            "type",
            "title",
            "comment",
            "timestamp",
            "user",
            "bot",
            "minor",
            "patrolled",
            "length_old",
            "length_new",
            "revision_old",
            "revision_new",
            "server_url",
            "server_name",
            "wiki",
//            "parsed_comment_span_dir_auto",
//            "parsed_comment_span_class_auto_comment",
    };
}
