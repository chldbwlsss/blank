package ddobab.blank.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class AnswerSaveRequestDto {

    private Long userNo;
    private Long questionNo;
    private String content;
//    private List<MultipartFile> answerImgFiles;

    @Builder
    public AnswerSaveRequestDto(String content, Long questionNo, Long userNo) {
        this.content = content;
        this.questionNo = questionNo;
        this.userNo = userNo;
    }

    public void setUserNo(Long userNo){
        this.userNo = userNo;
    }
}
