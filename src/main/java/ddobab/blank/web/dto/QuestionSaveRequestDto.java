package ddobab.blank.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
public class QuestionSaveRequestDto {

    private Long userNo;
    private String content;
    private String categoryValue;
    private List<MultipartFile> questionImgFiles;

    @Builder
    public QuestionSaveRequestDto(String content, String categoryValue, List<MultipartFile> questionImgFiles) {
        this.content = content;
        this.categoryValue = categoryValue;
        this.questionImgFiles = questionImgFiles;
    }

}
