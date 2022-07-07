package ddobab.blank.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerRequestDto {

    private Long questionNo;
    private String content;
//    private List<MultipartFile> answerImgFiles;

    @Builder
    public AnswerRequestDto(String content, Long questionNo) {
        this.content = content;
        this.questionNo = questionNo;
    }
}
