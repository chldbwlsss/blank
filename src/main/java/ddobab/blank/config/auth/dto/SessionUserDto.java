package ddobab.blank.config.auth.dto;

import ddobab.blank.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUserDto implements Serializable {

    private static final long serialVersionUID = 8385617491429165861L;

    private Long userNo;
    private String nickname;
    private String email;
    private String profileImgUrl;

    public SessionUserDto(User user) {
        this.userNo = user.getNo();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.profileImgUrl = user.getProfileImgUrl();
    }
}
