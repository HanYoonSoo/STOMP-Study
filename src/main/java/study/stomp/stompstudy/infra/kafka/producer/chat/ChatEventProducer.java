package study.stomp.stompstudy.infra.kafka.producer.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import study.stomp.stompstudy.infra.kafka.producer.chat.event.NormalChatEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatEventProducer {

    @Value("${spring.kafka.topic.normal-chat}")
    private String normalChatTopic;

    private final KafkaTemplate<String, NormalChatEvent> normalChatKafkaTemplate;

    public void sendToNormalChatTopic(NormalChatEvent message){
        normalChatKafkaTemplate.send(normalChatTopic, message);
    }
}
