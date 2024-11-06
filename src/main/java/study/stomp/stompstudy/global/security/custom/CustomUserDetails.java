package study.stomp.stompstudy.global.security.custom;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import study.stomp.stompstudy.domain.model.RoleType;
import study.stomp.stompstudy.domain.user.domain.User;

import java.util.Collection;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CustomUserDetails extends User implements UserDetails {

    private Long id;
    private String loginId;
    private RoleType roleType;
    private String password;
    private String nickName;

    private CustomUserDetails(User user){
        this.id = user.getUserId();
        this.loginId = user.getLoginId();
        this.password = user.getPassword();
        this.roleType = user.getRoleType();
        this.nickName = user.getNickName();
    }

    private CustomUserDetails(String loginId, RoleType role){
        this.loginId = loginId;
        this.roleType = role;
    }

    private CustomUserDetails(String loginId, RoleType role, String password) {
        this.loginId = loginId;
        this.roleType = role;
        this.password = password;
    }

    public static CustomUserDetails from(User user){
        return new CustomUserDetails(user);
    }

    public static CustomUserDetails of(String loginId, RoleType role){
        return new CustomUserDetails(loginId, role);
    }

    public static CustomUserDetails of(String email, RoleType role, String password) {
        return new CustomUserDetails(email, role, password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return CustomAuthorityUtils.createAuthorities(roleType);
    }

    @Override
    public String getUsername() {
        return this.loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
