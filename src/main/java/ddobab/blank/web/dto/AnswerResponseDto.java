package ddobab.blank.web.dto;

import ddobab.blank.domain.answer.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AnswerResponseDto {

    private Long no;
    private String content;
    private String writer;
    private Long writerNo;

    public AnswerResponseDto(Answer answer) {
        this.no = answer.getNo();
        this.content = answer.getContent();
        this.writer = answer.getUser().getNickname();
        this.writerNo = answer.getUser().getNo();
    }
}
