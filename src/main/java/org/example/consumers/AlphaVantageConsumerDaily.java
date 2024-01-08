package org.example.consumers;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.example.types.StockData;
import org.example.types.StockDataDaily;
import org.example.util.CsvWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.example.util.Constants.*;

public class AlphaVantageConsumerDaily extends ConsumerAbstract{
    private static final Logger logger = Logger.getLogger(AlphaVantageConsumerMonthly.class.getSimpleName());
    private static final String CSV_FILE_PATH = "/home/ijevtic/Desktop/daily" + SYMBOL + ".csv";


    public static void main(String[] args) throws IOException {
        logger.info("Kafka Simple Consumer");

        String groupId = "g3";
        String topic = DAILY_TOPIC;

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getConsumerProperties(groupId));

        consumer.subscribe(List.of(topic));

        List<StockDataDaily> stockDataEntries = new ArrayList<>();

        int i = 0;
        while (i < 100) {
            i++;
            logger.info("Polling...");
            consumer.poll(100).forEach(record -> {
                try {
                    StockDataDaily w = StockDataDaily.fromJson(record.value(), objectMapper);
                    w.setDate(record.key());
                    logger.info(w.toString());
                    stockDataEntries.add(w);

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
            for (StockDataDaily entry : stockDataEntries) {
                String[] data = CsvWriter.getDataFromStockData(entry);
                if(data == null) continue;

                writer.write(String.join(",", data));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static String[] header = {
            "open",
            "high",
            "low",
            "close",
            "volume",
            "date",
    };
}
