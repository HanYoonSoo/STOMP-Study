package study.stomp.stompstudy.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.stomp.stompstudy.global.config.event.Events;

@Configuration
@RequiredArgsConstructor
public class EventsConfig {

    private final ApplicationContext applicationContext;

    @Bean
    public InitializingBean eventInitializer(){
        return () -> Events.register(applicationContext);
    }
}
