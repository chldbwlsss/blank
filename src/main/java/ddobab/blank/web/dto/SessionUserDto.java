package ddobab.blank.web.dto;

import java.io.Serializable;

public class SessionUserDto implements Serializable {

    private static final long serialVersionUID = 3138786302999484192L;

    private Long userNo;
    private String nickname;
    private String email;
    private String profileImgUrl;

    public SessionUserDto(Long userNo, String nickname, String email, String profileImgUrl) {
        this.userNo = userNo;
        this.nickname = nickname;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
    }
}
