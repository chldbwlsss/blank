package ddobab.blank.domain.answer;

import ddobab.blank.domain.BaseTimeEntity;
import ddobab.blank.domain.question.Question;
import ddobab.blank.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@DynamicInsert
@Getter
@NoArgsConstructor
@Entity
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(length = 300, nullable = false)
    private String replyContent;

    @ManyToOne
    @JoinColumn(name = "QUESTION_NO")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "USER_NO")
    private User user;
}
