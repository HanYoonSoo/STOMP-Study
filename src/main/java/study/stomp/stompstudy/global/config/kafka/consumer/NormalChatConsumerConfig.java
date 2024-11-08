package study.stomp.stompstudy.global.config.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import study.stomp.stompstudy.infra.kafka.producer.chat.event.NormalChatEvent;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class NormalChatConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${spring.kafka.consumer.group-id.normal-chat}")
    private String groupId;

    @Bean
    public Map<String, Object> normalChatConsumerConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return config;
    }

    @Bean
    public ConsumerFactory<String, NormalChatEvent> normalChatEventConsumerFactory(){
        return new DefaultKafkaConsumerFactory<>(
                normalChatConsumerConfiguration(),
                new StringDeserializer(),
                new JsonDeserializer<>(NormalChatEvent.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NormalChatEvent> normalChatListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, NormalChatEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(normalChatEventConsumerFactory());
        return factory;
    }
}
