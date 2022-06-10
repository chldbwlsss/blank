package ddobab.blank.web.dto;

import ddobab.blank.domain.question.Question;
import lombok.Getter;

@Getter
public class QuestionResponseDto {

    private Long no;
    private String content;
    private String writer;

    public QuestionResponseDto(Question entity) {
        this.no = entity.getNo();
        this.content = entity.getContent();
        this.writer = entity.getWriter();
    }
}
