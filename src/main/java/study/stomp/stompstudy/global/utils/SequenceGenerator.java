package study.stomp.stompstudy.global.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import study.stomp.stompstudy.domain.model.AutoIncrementSequence;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component
@RequiredArgsConstructor
public class SequenceGenerator {

    private final MongoOperations mongoOperations;

    /**
     * 1. "_id"가 seqName인 문서를 찾습니다.
     * 2. "seq" 값을 1 증가시킵니다.
     * 3. 새 값을 반환하고, 문서가 없으면 생성합니다. -> returnNew(true) - 업데이트 이후의 새로운 값 반환, upsert(true) - _id가 seqName인 문서가 존재하지 않으면 새 문서 생성, 이 경우 기본 시퀀스 1
     * 4. 결과를 AutoIncrementSequence 객체로 반환합니다.
     * 5. 시퀀스 값 또는 기본값 1을 반환합니다.
     */
    public Long generateSequence(String seqName) {
        AutoIncrementSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq", 1), options().returnNew(true).upsert(true), AutoIncrementSequence.class);

        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

}
