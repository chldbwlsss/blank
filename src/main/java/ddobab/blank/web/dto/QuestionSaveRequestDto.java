package ddobab.blank.web.dto;

import ddobab.blank.domain.question.Question;
import ddobab.blank.domain.question.QuestionCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionSaveRequestDto {

    private String content;
    private String writer;
    private String categoryValue;

    @Builder
    public QuestionSaveRequestDto(String content, String writer, String categoryValue) {
        this.content = content;
        this.writer = writer;
        this.categoryValue = categoryValue;
    }

    public Question toEntity() {
        return Question.builder()
                .content(content)
                .writer(writer)
                .category(QuestionCategory.valueOf(categoryValue))
                .build();
    }
}
