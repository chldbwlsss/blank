package ddobab.blank.domain.question;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class QuestionImg {

    @Column(name = "QUESTION_IMG_NO")
    @Id
    private Long no;

    @ManyToOne
    @JoinColumn(name = "QUESTION_NO")
    private Question question;

    private String questionImgUrl;

    @Builder
    public QuestionImg(Question question, String questionImgUrl) {
        this.question = question;
        this.questionImgUrl = questionImgUrl;
    }
}
