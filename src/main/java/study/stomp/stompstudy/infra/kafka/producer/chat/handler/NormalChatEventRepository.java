package study.stomp.stompstudy.infra.kafka.producer.chat.handler;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import study.stomp.stompstudy.infra.kafka.producer.chat.event.NormalChatEvent;

import java.util.Optional;

@Repository
public interface NormalChatEventRepository extends MongoRepository<NormalChatEvent, Long> {

    Optional<NormalChatEvent> findByUuid(String uuid);
}
