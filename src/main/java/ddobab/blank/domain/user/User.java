package ddobab.blank.domain.user;

import ddobab.blank.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@DynamicInsert
@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id @Column(name = "USER_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column
    private String profileImgUrl;

    @Builder
    public User(String nickname, String email, String profileImgUrl) {
        this.nickname = nickname;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
    }
}
