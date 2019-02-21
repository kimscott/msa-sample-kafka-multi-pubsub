package com.example.template;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.ArrayList;
import java.util.Random;

@Service
public class KafkaSenderTwo {

    @Value("${topic.cloudInstance}")
    private String cloudInstance;

    @Value("${producerReplicas}")
    private String producerReplicas;

    @Value("${producerId}")
    private String producerId;

    @Autowired
    KafkaTemplate kafkaTemplate;

    public void send(){

        ArrayList<String> statusList = new ArrayList<String>();
        statusList.add("Ready");
        statusList.add("Pending");
        statusList.add("Running");
        statusList.add("Completed");
        statusList.add("Crash");
        statusList.add("Deleted");
        // make random data
//        while(true) {
            Random random = new Random();
            for (int i = 0; i < 1000; i++) {
                String accountId = "accountId_" + i;
                String status = statusList.get(random.nextInt(6));
                JSONObject data = new JSONObject();
                data.put("accountId", accountId);
                data.put("region", "ap-northeast-2");
                data.put("status", status);

                System.out.println("Message: " + data.toString() + " sent to topic: " + cloudInstance);
                this.sendMessage(data.toString());
            }
//        }

    }

    public void sendMessage(String message) {

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(cloudInstance, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + message + "] due to : " + ex.getMessage());
            }
        });
    }
}
