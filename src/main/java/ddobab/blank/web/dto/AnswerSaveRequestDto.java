package ddobab.blank.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
public class AnswerSaveRequestDto {

    private String content;
    private Long questionId;
    private Long userId;
    private List<MultipartFile> answerImgFiles;

    @Builder
    public AnswerSaveRequestDto(String content, Long questionId, Long userId, List<MultipartFile> answerImgFiles) {
        this.content = content;
        this.questionId = questionId;
        this.userId = userId;
        this.answerImgFiles = answerImgFiles;
    }
}
