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
@Table(name="USERS")
@Entity
public class User extends BaseTimeEntity {

    @Column(name = "USER_NO")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column
    private String profileImgUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String nickname, String email, String profileImgUrl, Role role) {
        this.nickname = nickname;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.role = role;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }

    public void updateUser(String nickname) {
        this.nickname = nickname;
//        this.profileImgUrl = profileImgUrl;
    }
}
