package study.stomp.stompstudy.global.security.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import study.stomp.stompstudy.domain.model.RoleType;

import java.util.List;

@Slf4j
public class CustomAuthorityUtils {

    public static List<GrantedAuthority> createAuthorities(RoleType role){
        return List.of(new SimpleGrantedAuthority(role.getRole()));
    }
}
