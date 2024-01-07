package org.example.producers;

import org.apache.kafka.clients.producer.ProducerConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

public abstract class AlphaProducerAbstract {
    // Define the property names
    static final String ALPHA_VANTAGE_KEY_PROPERTY = "alpha_vantage_api_key";

    // Store the loaded properties
    static final Properties properties = new Properties();

    // Load properties when the class is initialized
    static {
        try (InputStream input = AlphaVantageProducerMonthly.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                System.err.println("Unable to find application.properties file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Access the property value
    static final String alphaVantageKey = properties.getProperty(ALPHA_VANTAGE_KEY_PROPERTY);
    static public String fetchDataFromApi(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        StringBuilder response = new StringBuilder();

        try (Scanner scanner = new Scanner(con.getInputStream())) {
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
//                response.append("\n");
            }
        }
        return response.toString();
    }

    static public Properties getProducerProperties() {
        Properties properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class.getName());

        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20");
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024));
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        return properties;
    }
}

