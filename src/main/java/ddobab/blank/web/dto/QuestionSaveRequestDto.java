package ddobab.blank.web.dto;

import ddobab.blank.domain.question.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionSaveRequestDto {

    private String content;
    private String writer;

    @Builder
    public QuestionSaveRequestDto(String content, String writer) {
        this.content = content;
        this.writer = writer;
    }

    public Question toEntity() {
        return Question.builder()
                .content(content)
                .writer(writer)
                .build();
    }
}
