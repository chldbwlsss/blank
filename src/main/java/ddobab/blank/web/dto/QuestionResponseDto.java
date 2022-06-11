package ddobab.blank.web.dto;

import ddobab.blank.domain.question.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class QuestionResponseDto {

    private Long no;
    private String content;
    private String writer;
    private Integer views;

    public QuestionResponseDto(Question question) {
        this.no = question.getNo();
        this.content = question.getContent();
        this.writer = question.getWriter();
        this.views = question.getViews();
    }
}
