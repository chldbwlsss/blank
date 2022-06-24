package ddobab.blank.security.dto;

import ddobab.blank.domain.user.User;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class SessionUserDto implements Serializable {

    private static final long serialVersionUID = 8385617491429165861L;

    private Long no;
    private String nickname;
    private String email;
    private String profileImgUrl;

    public SessionUserDto(User user) {
        this.no = user.getNo();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.profileImgUrl = user.getProfileImgUrl();
    }
}
