package study.stomp.stompstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class StompStudyApplication {

    static{
        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true"); // 설정 추가
    }

    public static void main(String[] args) {
        SpringApplication.run(StompStudyApplication.class, args);
    }

}
