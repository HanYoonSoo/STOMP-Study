package study.stomp.stompstudy.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.stomp.stompstudy.domain.user.dto.response.UserInfoResponse;
import study.stomp.stompstudy.domain.user.service.UserQueryService;
import study.stomp.stompstudy.global.common.dto.response.DataResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserQueryController {

    private final UserQueryService userQueryService;

    @GetMapping("/{userId}")
    public DataResponseDto read(@PathVariable("userId") Long userId){
        UserInfoResponse response = userQueryService.read(userId);

        return DataResponseDto.from(response);
    }

    @GetMapping("/code/{userId}")
    public DataResponseDto readUserCode(@PathVariable("userId") Long userId){
        String response = userQueryService.getUserCode(userId);

        return DataResponseDto.from(response);
    }
}
