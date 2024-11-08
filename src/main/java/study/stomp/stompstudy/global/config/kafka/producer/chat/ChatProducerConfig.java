package study.stomp.stompstudy.global.config.kafka.producer.chat;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import study.stomp.stompstudy.infra.kafka.producer.chat.event.NormalChatEvent;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class ChatProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;;

    @Bean
    public Map<String, Object> chatEventProducerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return config;
    }

    @Bean
    public ProducerFactory<String, NormalChatEvent> normalChatEventProducerFactory(){
        return new DefaultKafkaProducerFactory<>(chatEventProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, NormalChatEvent> normalChatKafkaTemplate(){
        return new KafkaTemplate<>(normalChatEventProducerFactory());
    }

}
