package ddobab.blank.web.dto;

import ddobab.blank.domain.question.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class QuestionResponseDto {

    private Long no;
    private String categoryValue;
    private String content;
    private String writer;
    private Integer views;

    public QuestionResponseDto(Question question) {
        this.no = question.getNo();
        this.categoryValue = String.valueOf(question.getCategory());
        this.content = question.getContent();
        this.writer = question.getUser().getNickname();
        this.views = question.getViews();
    }
}
