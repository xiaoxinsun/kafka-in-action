package org.kia;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.bot.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class SensorConsumerMain {

    private static final Logger log = LoggerFactory.getLogger(SensorConsumerMain.class);

    private static volatile boolean keepConsuming = true;

    public static void main(String[] args) {
        Properties kaProp = new Properties();
        kaProp.put("bootstrap.servers", "172.31.201.69:9092");
        kaProp.put("key.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");
        kaProp.put("value.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        kaProp.put("schema.registry.url", "http://172.31.201.69:8081");
        kaProp.put(CommonClientConfigs.GROUP_ID_CONFIG, "billy_consumer");
        kaProp.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        consume(kaProp);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> keepConsuming = false));
    }

    private static void consume(Properties kaProp) {
        try (KafkaConsumer<Long, Alert> consumer = new KafkaConsumer<Long, Alert>(kaProp)) {
            consumer.subscribe(List.of("kia_schematest"));
            while (keepConsuming) {
                ConsumerRecords<Long, Alert> records = consumer.poll(Duration.ofSeconds(1));
                for (ConsumerRecord<Long, Alert> record : records) {
                    log.info("Received at offset = {}, value = {}", record.offset(), record.value());
                }
            }
        }
    }

}
