package ddobab.blank.web.dto;

import ddobab.blank.domain.question.QuestionCategory;
import ddobab.blank.domain.question.QuestionImg;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class QuestionUpdateRequestDto {

    private String content;
    private String categoryValue;
//    private List<String> questionImgUrls;

    @Builder
    public QuestionUpdateRequestDto(String content, String categoryValue) {
        this.content = content;
        this.categoryValue = categoryValue;
    }
}
