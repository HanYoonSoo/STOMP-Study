package study.stomp.stompstudy.domain.message.normal.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import study.stomp.stompstudy.domain.message.normal.domain.NormalMessage;

@Repository
public interface NormalMessageRepository extends MongoRepository<NormalMessage, Long> {

    @Query("{'normalId':  ?0, 'isDeleted': false}")
    Slice<NormalMessage> findByNormalIdAndIsDeleted(Long normalId, Pageable pageable);
}
