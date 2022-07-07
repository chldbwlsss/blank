package ddobab.blank.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequestDto {

    private String nickname;
//    private String profileImgUrl;

    @Builder
    public UserRequestDto(String nickname) {
        this.nickname = nickname;
    }
}
