package study.stomp.stompstudy.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChatType {

    NORMAL("기본 채팅 메시지");

    private final String type;
}
