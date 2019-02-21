package com.example.template;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static java.time.Duration.ofSeconds;
import static java.util.Collections.singleton;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;


@Service
public class KafkaReceiverTwo {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaReceiverTwo.class);

    @Value("${topic.cloudInstance}")
    private String cloudInstance;

    /**
     * 이 아래 부분은 10초단위로 데이터를 묶어서 메세지를 pull 한다.
     * AUTO_OFFSET_RESET_CONFIG 내용이 없어서 시작할때마다 처음부터 모든 데이터를 가져온다.
     */

//    @Bean
    public void consume(){
        KafkaConsumer<String, String> consumer = createKafkaConsumer();

        while (true) {
            int second = 10;
            ConsumerRecords<String, String> records = consumer.poll(ofSeconds(second));

            Map<TopicPartition, OffsetAndMetadata> offsetsToCommit = new HashMap<>();

            for (TopicPartition partition : records.partitions()) {
                List<ConsumerRecord<String, String>> partitionedRecords = records.records(partition);

                for(ConsumerRecord<String, String> record : partitionedRecords){
                    LOG.info("offset='{}' value='{}' ", record.offset(), record.value());
                }
            }

            LOG.info("===================== {} 초 여백 ==================== " , second);
        }
    }
    private  KafkaConsumer<String, String> createKafkaConsumer() {
        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(GROUP_ID_CONFIG, "foo");
        props.put(ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ISOLATION_LEVEL_CONFIG, "read_committed");
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(singleton(cloudInstance));
        return consumer;
    }
}
