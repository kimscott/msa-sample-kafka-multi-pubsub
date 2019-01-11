package com.example.template;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
public class KafkaSender {

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

        // 변수값은 string 으로 받는것이 여러 오류를 줄여주니 int 형으로 변환을 시켜준다
        int producerReplicasVal = 1;
        int producerIdVal = 0;
        try{
            producerReplicasVal = Integer.parseInt(producerReplicas);
            producerIdVal = Integer.parseInt(producerId);
        }catch (Exception ex){

        }

        Random random = new Random();
        for(int i=0; i < 1000; i++){
            if( (i % producerReplicasVal) == producerIdVal) {

                String accountId = "accountId_" + i;
                String status = statusList.get(random.nextInt(6));
                JSONObject data = new JSONObject();
                data.put("accountId", accountId);
                data.put("region", "ap-northeast-2");
                data.put("status", status);

                System.out.println("Message: " + data.toString() + " sent to topic: " + cloudInstance);
                kafkaTemplate.send(cloudInstance, accountId, data.toString());

                // api 를 쏘는 부분 - 병목이 생긴다고 가정함
                try {
//                    Thread.sleep(200);
                } catch (Exception e) {

                }
            }
        }


    }
}
