package study.stomp.stompstudy.domain.normal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import study.stomp.stompstudy.domain.normal.domain.Normal;

@Repository
public interface NormalRepository extends MongoRepository<Normal, Long> {
}
