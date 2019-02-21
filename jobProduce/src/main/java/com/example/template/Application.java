package com.example.template;

import org.json.simple.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SpringBootApplication
public class Application {

    protected static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(Application.class, args);

//        KafkaSender kafkaSender = Application.applicationContext.getBean(KafkaSender.class);
//        kafkaSender.send();
//        kafkaSender.sendByClass();


        KafkaSenderTwo kafkaSender2 = Application.applicationContext.getBean(KafkaSenderTwo.class);
        kafkaSender2.send();

        SpringApplication.exit(applicationContext);
    }
}

