package ddobab.blank.domain.question;


import ddobab.blank.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

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

    @Builder
    public Question(String content, String writer) {
        this.content = content;
        this.writer = writer;
    }

    public void update(String content) {
        this.content = content;
    }
}
