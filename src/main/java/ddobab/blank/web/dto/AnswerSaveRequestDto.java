package ddobab.blank.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class AnswerSaveRequestDto {

    private Long questionNo;
    private String content;
//    private List<MultipartFile> answerImgFiles;

    @Builder
    public AnswerSaveRequestDto(String content, Long questionNo) {
        this.content = content;
        this.questionNo = questionNo;
    }
}
