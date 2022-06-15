package ddobab.blank.domain.question;


import ddobab.blank.domain.BaseTimeEntity;
import ddobab.blank.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@DynamicInsert
@Getter
@NoArgsConstructor
@Entity
public class Question extends BaseTimeEntity {

    @Column(name = "QUESTION_NO")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(columnDefinition = "Text", length = 500, nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "USER_NO")
    private User user;

    @ColumnDefault("0")
    private Integer views;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionCategory category;

    @Builder
    public Question(String content, User user, QuestionCategory category) {
        this.content = content;
        this.user = user;
        this.category = category;
    }

    public void updateQuestion(String content, QuestionCategory category) {
        this.content = content;
        this.category = category;
    }

    public void plusViews() {
        this.views += 1;
    }
}
