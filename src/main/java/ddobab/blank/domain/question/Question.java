package ddobab.blank.domain.question;


import ddobab.blank.domain.BaseTimeEntity;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(columnDefinition = "Text", length = 500, nullable = false)
    private String content;

    @Column(nullable = false)
    private String writer;

    @ColumnDefault("0")
    private Integer views;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionCategory category;

    @Builder
    public Question(String content, String writer, QuestionCategory category) {
        this.content = content;
        this.writer = writer;
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
