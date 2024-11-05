package study.stomp.stompstudy.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {

    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private final String role;
}
