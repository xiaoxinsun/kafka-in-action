package org.kia;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.bot.Alert;
import org.bot.AlertStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Properties;

public class SensorProducerMain {

    private static final Logger log = LoggerFactory.getLogger(SensorProducerMain.class);

    public static void main(String[] args) {
        Properties kaProp = new Properties();
        kaProp.put("bootstrap.servers", "172.31.201.69:9092");
        kaProp.put("key.serializer", "org.apache.kafka.common.serialization.LongSerializer");
        kaProp.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
        kaProp.put("schema.registry.url", "http://172.31.201.69:8081");

        try (Producer<Long, Alert> producer = new KafkaProducer<>(kaProp)) {
            Alert alert = new Alert(22345L, Instant.now().toEpochMilli(), AlertStatus.Warning);
            ProducerRecord<Long, Alert> producerRecord = new ProducerRecord<>("kia_schematest", alert.getSensorId(), alert);
            producer.send(producerRecord);
            log.info("Alert sent");
        }
    }

}
