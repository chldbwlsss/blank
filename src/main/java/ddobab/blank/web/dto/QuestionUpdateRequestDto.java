package ddobab.blank.web.dto;

import ddobab.blank.domain.question.QuestionCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionUpdateRequestDto {

    private String content;
    private String categoryValue;

    @Builder
    public QuestionUpdateRequestDto(String content, String categoryValue) {
        this.content = content;
        this.categoryValue = categoryValue;
    }
}
