package study.stomp.stompstudy.domain.message.normal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import study.stomp.stompstudy.domain.message.normal.domain.NormalMessage;

@Repository
public interface NormalMessageRepository extends MongoRepository<NormalMessage, Long> {
}
