package study.stomp.stompstudy.domain.message.normal.dto.request;

import lombok.Getter;
import study.stomp.stompstudy.domain.file.domain.UploadFile;

import java.util.List;

@Getter
public class NormalMessageCreateRequest {

    private Long normalId;
    private Long userId;
    private String profileImg;
    private String writer;
    private String content;
    private List<UploadFile> files;
}

//{
//    "normalId": 7,
//    "userId" : 15,
//    "profileImg" : "https://www.naver.com",
//    "writer" : "test",
//    "content" : "hello"
//}