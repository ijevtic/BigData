package org.example.consumers;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.example.types.Earnings;
import org.example.util.CsvWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.example.util.Constants.EARNINGS_TOPIC;
import static org.example.util.Constants.SYMBOL;

public class AlphaVantageConsumerEarnings extends ConsumerAbstract{
    private static final Logger logger = Logger.getLogger(AlphaVantageConsumerEarnings.class.getSimpleName());

    private static final String CSV_FILE_PATH = "/home/ijevtic/Desktop/earnings" + SYMBOL + ".csv";

    public static void main(String[] args) throws IOException {
        logger.info("Kafka Simple Consumer");

        String groupId = "g2";
        String topic = EARNINGS_TOPIC;

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getConsumerProperties(groupId));

        consumer.subscribe(List.of(topic));

        List<Earnings> earningEntries = new ArrayList<>();

        int i = 0;
        while (i < 100) {
            i++;
            logger.info("Polling...");
            consumer.poll(100).forEach(record -> {
                try {
                    Earnings w = Earnings.fromJson(record.value(), objectMapper);
                    logger.info(w.toString());
                    earningEntries.add(w);

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
            for (Earnings entry : earningEntries) {
                String[] data = CsvWriter.getDataFromEarnings(entry);
                if(data == null) continue;

                writer.write(String.join(",", data));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static String[] header = {
            "date",
            "eps",
//            "parsed_comment_span_dir_auto",
//            "parsed_comment_span_class_auto_comment",
    };


}
