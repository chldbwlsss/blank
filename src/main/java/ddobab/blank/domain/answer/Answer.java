package ddobab.blank.domain.answer;

import ddobab.blank.domain.BaseTimeEntity;
import ddobab.blank.domain.question.Question;
import ddobab.blank.domain.user.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@DynamicInsert
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
public class Answer extends BaseTimeEntity {

    @Column(name = "ANSWER_NO")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(length = 500, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_NO")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO")
    private User user;

    @Builder
    public Answer(String content, Question question, User user) {
        this.content = content;
        this.question = question;
        this.user = user;
    }

    public void updateAnswer(String content) {
        this.content = content;
    }
}
