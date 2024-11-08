package study.stomp.stompstudy.domain.file.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadFile {

    private String storeFileName;
    private String originalFilename;

}