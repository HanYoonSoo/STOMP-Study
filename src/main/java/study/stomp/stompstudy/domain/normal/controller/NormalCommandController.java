package study.stomp.stompstudy.domain.normal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.stomp.stompstudy.domain.normal.dto.request.NormalAddUserRequest;
import study.stomp.stompstudy.domain.normal.dto.request.NormalCreateRequest;
import study.stomp.stompstudy.domain.normal.dto.response.NormalInfoResponse;
import study.stomp.stompstudy.domain.normal.service.NormalCommandService;
import study.stomp.stompstudy.global.common.dto.response.DataResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/normal")
public class NormalCommandController {

    private final NormalCommandService normalCommandService;

    @PostMapping
    public DataResponseDto save(@Valid @RequestBody NormalCreateRequest request){
        NormalInfoResponse response = normalCommandService.save(request);

        return DataResponseDto.from(response);
    }

    @PatchMapping("/user")
    public DataResponseDto addUser(@Valid @RequestBody NormalAddUserRequest request){
        normalCommandService.addUser(request);

        return DataResponseDto.from("Add user success");
    }
}
