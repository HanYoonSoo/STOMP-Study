package study.stomp.stompstudy.domain.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import study.stomp.stompstudy.domain.user.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
}
