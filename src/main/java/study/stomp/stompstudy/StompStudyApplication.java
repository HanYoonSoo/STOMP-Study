package study.stomp.stompstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class StompStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(StompStudyApplication.class, args);
    }

}
