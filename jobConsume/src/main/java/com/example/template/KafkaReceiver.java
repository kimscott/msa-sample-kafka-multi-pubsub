package com.example.template;

import com.google.gson.Gson;
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

//    @KafkaListener(topics = "${topic.cloudInstance}")
    public void listen(@Payload String message, Consumer<?, ?> consumer, ConsumerRecord<?, ?> consumerRecord) {
        // 데이터 베이스에 값을 넣을 경우에는 데이터를 저장 후에 수동 커밋을 해준다.
//        consumer.commitAsync();
        LOG.info("listen record='{}' message='{}'", consumerRecord.offset() , message);
    }


    /**
     * 특정 파티션의 데이터를 가져오는 방법
     * @param message
     * @param consumerRecord
     */
//    @KafkaListener(topicPartitions
//            = @TopicPartition(topic = "${topic.cloudInstance}", partitions = { "${topic.cloudInstance.partition}" })
//            , groupId = "bar")
    public void listenWithPartition(@Payload String message, ConsumerRecord<?, ?> consumerRecord) {
        LOG.info("listenWithPartition record='{}' message='{}' partition='{}'", consumerRecord.offset() , message, consumerRecord.partition());
    }

    /**
     * 특정 파티션을 받겠다고 선언을 안해 놓으면, consumer 수가 늘어날수록, 알아서 partition을 분배하여 간다.
     * @param message
     * @param consumerRecord
     */
//    @KafkaListener(topics = "${topic.cloudInstance}")
    public void listenWithOutPartition(@Payload String message, ConsumerRecord<?, ?> consumerRecord) {
        LOG.info("listenWithPartition record='{}' message='{}' partition='{}'", consumerRecord.offset() , message, consumerRecord.partition());
    }

    /**
     * 특정 클래스로 받으려면 아래와 같이 객체로 변환을 하면 된다.
     * 주의점은 프로듀서쪽의 객체를 잘 마추어야 한다. - 별로 추천하지 않음
     * @param message
     * @param consumerRecord
     */
//    @KafkaListener(topics = "${topic.cloudInstance}")
    public void listenByObject(@Payload String message, ConsumerRecord<?, ?> consumerRecord) {
        CloudInstance cloudInstance = new Gson().fromJson(message, CloudInstance.class);
        LOG.info("accountId='{}'  status='{}' " , cloudInstance.accountId , cloudInstance.status );
    }


    /**
     * 특정 파티션의 특정 오프셋으로 부터 토픽을 시작하는 방법
     */
//    @KafkaListener(topicPartitions
//            = @TopicPartition(topic = "${topic.cloudInstance}",
//            partitionOffsets = {
//                    @PartitionOffset(partition = "${topic.cloudInstance.partition}" , initialOffset = "110" )
//            }  )
//            , groupId = "bar1")
    public void listenWithSpecificOffset(@Payload String message, ConsumerRecord<?, ?> consumerRecord) {
        doDelay();
        LOG.info("listenWithSpecificOffset record='{}' message='{}' partition='{}'", consumerRecord.offset() , message, consumerRecord.partition());
    }

    /**
     * 특정 클래스로 받으려면 아래와 같이 객체로 변환을 하면 된다.
     */
//    @KafkaListener(topics = "${topic.cloudInstance}")
    public void listenByObject4(@Payload String message, ConsumerRecord<?, ?> consumerRecord) {
        LOG.info("headers='{}' ", consumerRecord.headers().toString());
        LOG.info("offset='{}' ", consumerRecord.offset());
        LOG.info("value='{}' ", consumerRecord.value());
        LOG.info("timestamp='{}' ", consumerRecord.timestamp());
    }

    /**
     * 이 아래 부분은 메세지를 받을때 선언된 Factory를 지정하는데
     * filterKafkaListenerContainerFactory 에는 특정 문구가 포함된 메세지는 거절하도록 구현되어있음
     */
//    @KafkaListener(
//            topics = "${topic.cloudInstance}",
//            containerFactory = "filterKafkaListenerContainerFactory")
    public void listen2(String message, ConsumerRecord<?, ?> consumerRecord) {
        LOG.info("value='{}' ", consumerRecord.value());
    }

    public void doDelay(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
