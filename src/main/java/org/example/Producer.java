package org.example;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.net.URI;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Producer {
    private static final Logger logger = Logger.getLogger(Producer.class.getSimpleName());

    public static void main(String[] args) throws InterruptedException {
        logger.info("Kafka Simple Producer");

        // create producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20");
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024));
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        String topic = "wikitopic";

        EventHandler eventHandler = new MediaChangeHandler(producer, topic);

        String url = "https://stream.wikimedia.org/v2/stream/recentchange";
        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));
        EventSource eventSource = builder.build();

        eventSource.start();

        TimeUnit.MINUTES.sleep(3);

        // create producer record
//        ProducerRecord<String, String> record1 = new ProducerRecord<>("topic1", "hello world");
//        ProducerRecord<String, String> record2 = new ProducerRecord<>("topic1", "druga poruka");



//        producer.send(record1);
//        producer.send(record2);
//
//        producer.flush();
//
//        producer.close();

    }

}
