package ddobab.blank.web.dto;

import ddobab.blank.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserResponseDto {

    private Long no;
    private String nickname;
    private String email;
    private String profileImgUrl;

    public UserResponseDto(User user) {
        this.no = user.getNo();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.profileImgUrl = user.getProfileImgUrl();
    }
}
