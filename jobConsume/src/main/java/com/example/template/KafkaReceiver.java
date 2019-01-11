package com.example.template;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;



@Service
public class KafkaReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaReceiver.class);

//    @KafkaListener(topics = "${topic.consumer.topicName}")
//    public void listen(@Payload String message, Consumer<?, ?> consumer, ConsumerRecord<?, ?> consumerRecord) {
//        doDelay();
//        // 데이터 베이스에 값을 넣을 경우에는 데이터를 저장 후에 수동 커밋을 해준다.
////        consumer.commitAsync();
//        LOG.info("listen record='{}' message='{}'", consumerRecord.offset() , message);
//    }


//    /**
//     * 특정 파티션의 데이터를 가져오는 방법
//     * @param message
//     * @param consumerRecord
//     */
//    @KafkaListener(topicPartitions
//            = @TopicPartition(topic = "${topic.consumerPartition.topicName}", partitions = { "${topic.consumerPartition.partition}" })
//            , groupId = "bar")
//    public void listenWithPartition(@Payload String message, ConsumerRecord<?, ?> consumerRecord) {
//        doDelay();
//        LOG.info("listenWithPartition record='{}' message='{}' partition='{}'", consumerRecord.offset() , message, consumerRecord.partition());
//    }

    /**
     * 특정 파티션을 받겠다고 선언을 안해 놓으면, consumer 수가 늘어날수록, 알아서 partition을 분배하여 간다.
     * @param message
     * @param consumerRecord
     */
    @KafkaListener(topics = "${topic.cloudInstance}")
    public void listenWithOutPartition(@Payload String message, ConsumerRecord<?, ?> consumerRecord) {
        doDelay();
        LOG.info("listenWithPartition record='{}' message='{}' partition='{}'", consumerRecord.offset() , message, consumerRecord.partition());
    }


    /**
     * 특정 파티션의 특정 오프셋으로 부터 토픽을 시작하는 방법
     */
//    @KafkaListener(topicPartitions
//            = @TopicPartition(topic = "${topic.consumer.topicName}",
//            partitionOffsets = {
//                    @PartitionOffset(partition = "${topic.consumer.partition}" , initialOffset = "110" )
//            }  )
//            , groupId = "bar1")
//    public void listenWithSpecificOffset(@Payload String message, ConsumerRecord<?, ?> consumerRecord) {
//        doDelay();
//        LOG.info("listenWithSpecificOffset record='{}' message='{}' partition='{}'", consumerRecord.offset() , message, consumerRecord.partition());
//    }

    public void doDelay(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
