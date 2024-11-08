package study.stomp.stompstudy.domain.normal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import study.stomp.stompstudy.domain.normal.domain.Normal;

import java.util.Optional;

@Repository
public interface NormalRepository extends MongoRepository<Normal, Long> {

    @Query("{'normalId': ?0, 'isDeleted': false}")
    Optional<Normal> findByNormalId(Long normalId);

    @Query("{'normalId': ?0, 'userIds':  {$in : ?1}, 'isDeleted': false}")
    Optional<Normal> findByNormalIdAndInUserId(Long normalId, Long userId);
}
