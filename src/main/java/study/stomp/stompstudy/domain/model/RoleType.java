package study.stomp.stompstudy.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {

    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private final String role;

    public static RoleType fromRoleType(String roleType) {
        for (RoleType role : RoleType.values()) {
            if (role.getRole().equals(roleType)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role type: " + roleType);
    }
}
