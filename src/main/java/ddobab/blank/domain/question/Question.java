package ddobab.blank.domain.question;


import ddobab.blank.domain.BaseTimeEntity;
import ddobab.blank.domain.user.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@DynamicInsert
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
public class Question extends BaseTimeEntity {

    @Column(name = "QUESTION_NO")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO")
    private User user;

    @Column(columnDefinition = "Text", length = 500, nullable = false)
    private String content;

    @ColumnDefault("0")
    private Integer views;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionCategory category;

    @Builder
    public Question(User user, String content, QuestionCategory category, Integer views) {
        this.user = user;
        this.content = content;
        this.category = category;
        this.views = views;
    }

    public void updateQuestion(String content, QuestionCategory category) {
        this.content = content;
        this.category = category;
    }

    public void plusViews() {
        this.views += 1;
    }
}
