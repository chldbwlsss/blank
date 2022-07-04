package ddobab.blank.web.dto;

import ddobab.blank.domain.answer.Answer;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AnswerResponseDto {

    private Long no;
    private String content;
    private String writer;

    public AnswerResponseDto(Answer answer) {
        this.no = answer.getNo();
        this.content = answer.getContent();
        this.writer = answer.getUser().getNickname();
    }
}
