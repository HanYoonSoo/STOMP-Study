package study.stomp.stompstudy.domain.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import study.stomp.stompstudy.domain.user.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

    @Query("{'loginId': ?0, 'isDeleted': false}")
    Optional<User> findByLoginId(String loginId);
}
