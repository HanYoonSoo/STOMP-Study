package study.stomp.stompstudy.infra.kafka.producer.chat.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import study.stomp.stompstudy.domain.message.normal.dto.event.NormalChatCreateEvent;
import study.stomp.stompstudy.global.utils.SequenceGenerator;
import study.stomp.stompstudy.infra.kafka.producer.chat.ChatEventProducer;
import study.stomp.stompstudy.infra.kafka.producer.chat.event.EventSentType;
import study.stomp.stompstudy.infra.kafka.producer.chat.event.NormalChatEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class NormalChatEventHandler {

    private final SequenceGenerator sequenceGenerator;
    private final ChatEventProducer chatEventProducer;
    private final NormalChatEventRepository normalChatEventRepository;

    @TransactionalEventListener(classes = NormalChatCreateEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void normalChatCreateEventBeforeHandler(NormalChatCreateEvent chatCreateEvent){
        NormalChatEvent normalChatEvent = createNormalChatEvent(chatCreateEvent);
        normalChatEvent.generateSequence(sequenceGenerator.generateSequence(NormalChatEvent.SEQUENCE_NAME));
        normalChatEventRepository.save(normalChatEvent);
    }

    @Async
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = NormalChatCreateEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void normalChatCreateEventAfterHandler(NormalChatCreateEvent chatCreateEvent){
        NormalChatEvent normalChatEvent = createNormalChatEvent(chatCreateEvent);
        publishNormalChatEvent(normalChatEvent);
    }

    private void publishNormalChatEvent(NormalChatEvent normalChatEvent) {
        NormalChatEvent chatEvent = normalChatEventRepository.findByUuid(normalChatEvent.getUuid())
                .orElseThrow(() -> new RuntimeException("Normal Chat Event Not Found"));

        try{
            chatEventProducer.sendToNormalChatTopic(chatEvent);
            chatEvent.changeEventSentType(EventSentType.SEND_SUCCESS);
            normalChatEventRepository.save(chatEvent);
        } catch (Exception e){
            chatEvent.changeEventSentType(EventSentType.SEND_FAIL);
            normalChatEventRepository.save(chatEvent);
        }
    }

    private NormalChatEvent createNormalChatEvent(NormalChatCreateEvent chatCreateEvent) {
        return NormalChatEvent.from(chatCreateEvent, EventSentType.INIT);
    }

}
