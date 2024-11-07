package study.stomp.stompstudy.domain.normal.service;

import study.stomp.stompstudy.domain.normal.dto.request.NormalCreateRequest;
import study.stomp.stompstudy.domain.normal.dto.response.NormalInfoResponse;

public interface NormalCommandService {
    NormalInfoResponse save(NormalCreateRequest request);
}
