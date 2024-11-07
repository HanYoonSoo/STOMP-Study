package study.stomp.stompstudy.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import study.stomp.stompstudy.domain.user.dto.request.*;
import study.stomp.stompstudy.domain.user.dto.response.UserInfoResponse;
import study.stomp.stompstudy.domain.user.service.UserCommandService;
import study.stomp.stompstudy.global.common.dto.response.DataResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserCommandController {

    private final UserCommandService userCommandService;

    @PostMapping("/signup")
    public DataResponseDto save(@Valid @RequestBody UserCreateRequest request){
        UserInfoResponse response = userCommandService.save(request);
        return DataResponseDto.from(response);
    }

    @PatchMapping
    public DataResponseDto modify(@Valid @RequestBody UserModifyRequest request){
        UserInfoResponse response = userCommandService.modify(request);
        return DataResponseDto.from(response);
    }

    @DeleteMapping
    public DataResponseDto delete(@Valid @RequestBody UserDeleteRequest request) {
        userCommandService.delete(request);
        return DataResponseDto.from("User delete success");
    }

    @PatchMapping("/password")
    public DataResponseDto modifyPassword(@Valid @RequestBody UserPwModifyRequest request) {
        userCommandService.modifyPassword(request);
        return DataResponseDto.from("Password modify success");
    }

    @PatchMapping("/lost/password")
    public DataResponseDto lostPassword(@Valid @RequestBody UserPwLostRequest request){
        String response = userCommandService.lostPassword(request);
        return DataResponseDto.from(response);
    }
}
