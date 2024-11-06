package study.stomp.stompstudy.global.security.custom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.stomp.stompstudy.domain.user.domain.User;
import study.stomp.stompstudy.domain.user.repository.UserRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * loadByUsername() : 사용자 이름(email)을 입력받아 User에서 사용자 정보를 조회한다.
     * 조회한 User 객체가 존재하면 createUserDetails() 메서드를 사용해서 CusotmUserDetails 객체를 생성하고 반환한다.
     */
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        return CustomUserDetails.from(user);
    }

    private UserDetails createUserDetails(User user){
        return CustomUserDetails.from(user);
    }
}