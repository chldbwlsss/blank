package ddobab.blank.web.dto;

import ddobab.blank.domain.question.Question;
import ddobab.blank.domain.question.QuestionCategory;
import ddobab.blank.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
public class QuestionSaveRequestDto {

    private String content;
    private String categoryValue;
    private List<MultipartFile> questionImgFiles;

    @Builder
    public QuestionSaveRequestDto(String content, String categoryValue, List<MultipartFile> questionImgFiles) {
        this.content = content;
        this.categoryValue = categoryValue;
        this.questionImgFiles = questionImgFiles;
    }

    public Question toEntity() {
        return Question.builder()
                .content(content)
                .category(QuestionCategory.valueOf(categoryValue))
                .build();
    }
}
