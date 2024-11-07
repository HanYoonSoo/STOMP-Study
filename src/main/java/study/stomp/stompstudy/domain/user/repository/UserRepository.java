package study.stomp.stompstudy.domain.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import study.stomp.stompstudy.domain.user.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

    @Query("{'userId': ?0, 'isDeleted': false}")
    Optional<User> findByUserId(Long userId);

    @Query("{'loginId': ?0, 'isDeleted': false}")
    Optional<User> findByLoginId(String loginId);

    @Query("{'userCode': ?0, 'isDeleted': false}")
    Optional<User> findByUserCode(String userCode);

    boolean existsByLoginIdAndIsDeletedFalse(String loginId);

    @Query("{'userCode': {$in: ?0}, 'isDeleted': false}")
    List<User> findAllByUserCodes(List<String> userCodes);

    @Query("{'userCode': {$in: ?0}, 'chatIds': {$nin: ?1}, 'isDeleted': false}")
    List<User> findAllByUserCodesAndNotInNormalId(List<String> userCodes, List<Long> normalId);

//    @Query("{'userId': ?0, 'isDeleted': false}")
//    List<Long> findChatIdsByUserId(Long userId);
}
