package ddobab.blank.web.dto;

import ddobab.blank.domain.question.QuestionCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionUpdateRequestDto {

    private String content;
    private QuestionCategory category;

    @Builder
    public QuestionUpdateRequestDto(String content, QuestionCategory category) {
        this.content = content;
        this.category = category;
    }
}
