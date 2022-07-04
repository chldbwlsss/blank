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
    private Long writerNo;
    private Integer views;

    public QuestionResponseDto(Question question) {
        this.no = question.getNo();
        this.categoryValue = question.getCategory().getKorValue();
        this.content = question.getContent();
        this.writer = question.getUser().getNickname();
        this.writerNo = question.getUser().getNo();
        this.views = question.getViews();
    }
}
