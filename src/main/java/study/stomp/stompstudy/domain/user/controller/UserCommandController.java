package study.stomp.stompstudy.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.stomp.stompstudy.domain.user.dto.request.UserCreateRequest;
import study.stomp.stompstudy.domain.user.dto.response.UserInfoResponse;
import study.stomp.stompstudy.domain.user.service.UserCommandService;
import study.stomp.stompstudy.global.common.dto.DataResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserCommandController {

    private UserCommandService userCommandService;

    @PostMapping("/signup")
    public DataResponseDto save(@Valid @RequestBody UserCreateRequest request){
        UserInfoResponse response = userCommandService.save(request);

        return DataResponseDto.from(response);
    }
}
