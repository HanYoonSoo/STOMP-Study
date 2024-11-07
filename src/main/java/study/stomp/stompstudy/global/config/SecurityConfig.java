package study.stomp.stompstudy.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import study.stomp.stompstudy.global.common.service.RedisService;
import study.stomp.stompstudy.global.security.custom.*;
import study.stomp.stompstudy.global.security.jwt.*;
import study.stomp.stompstudy.global.utils.UrlUtil;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    private final RedisService redisService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.httpBasic(HttpBasicConfigurer::disable)
                .headers((headerConfig) -> headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .formLogin(FormLoginConfigurer::disable)
                .logout(LogoutConfigurer::disable)
                .sessionManagement(sessionConfigurer -> sessionConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(entryPointConfigurer -> entryPointConfigurer.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .exceptionHandling(accessDeniedHandler -> accessDeniedHandler.accessDeniedHandler(new CustomAccessDeniedHandler()))
                .with(new CustomFilterConfigurer(), Customizer.withDefaults())
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource())) // ⭐️⭐️⭐️
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(UrlUtil.PERMITTED_URLS.toArray(new String[0])).permitAll()
                                .anyRequest().hasAnyRole("USER", "ADMIN")
                );

        return httpSecurity.build();
    }

    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedOriginPatterns(Collections.singletonList("*")); // ⭐️ 허용할 origin
        config.setAllowCredentials(true);
        config.setExposedHeaders(List.of("Authorization", "Refresh"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager,
                    jwtProvider, redisService);

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtProvider, redisService);

            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler(objectMapper));
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new LoginFailureHandler());

            builder
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(new JwtReissueTokenFilter(jwtProvider, redisService), JwtAuthenticationFilter.class)
                    .addFilterBefore(new JwtExceptionFilter(objectMapper), JwtReissueTokenFilter.class)
                    .addFilterAfter(new CustomLogoutFilter(redisService, jwtProvider), JwtAuthenticationFilter.class)
                    .addFilterAfter(jwtVerificationFilter, CustomLogoutFilter.class);
        }
    }

}