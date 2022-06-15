package ddobab.blank.domain.answer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class AnswerImg {

    @Column(name = "ANSWER_NO")
    @Id
    private Long no;

    @ManyToOne
    @JoinColumn(name = "ANSWER_NO")
    private Answer answer;

    private String answerImgUrl;

    @Builder
    public AnswerImg(Answer answer, String answerImgUrl) {
        this.answer = answer;
        this.answerImgUrl = answerImgUrl;
    }
}
