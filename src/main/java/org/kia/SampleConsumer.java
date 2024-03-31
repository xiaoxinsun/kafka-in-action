package org.kia;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class SampleConsumer {

    private Logger log = LoggerFactory.getLogger(SampleConsumer.class);

    private volatile boolean keepConsuming = true;

    public static void main(String[] args) {
        Properties kaProperties = new Properties();  //<1>
        kaProperties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092,localhost:9093,localhost:9094");
        kaProperties.put(CommonClientConfigs.GROUP_ID_CONFIG, "billy_consumer");
        kaProperties.put("enable.auto.commit", "true");
        kaProperties.put("auto.commit.interval.ms", "1000");
        kaProperties.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        kaProperties.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        kaProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        SampleConsumer sampleConsumer = new SampleConsumer();
        sampleConsumer.consume(kaProperties);
        Runtime.getRuntime().addShutdownHook(new Thread(sampleConsumer::shutdown));
    }

    private void consume(Properties kaProperties) {
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kaProperties)) {
            consumer.subscribe(List.of("kia_topic"));  //<2>

            while (keepConsuming) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(250));  //<3>
                for (ConsumerRecord<String, String> record : records) {   //<4>
                    log.info("kinaction_info offset = {}, kinaction_value = {}",
                            record.offset(), record.value());
                }
            }
        }
    }

    private void shutdown() {
        keepConsuming = false;
    }
}
