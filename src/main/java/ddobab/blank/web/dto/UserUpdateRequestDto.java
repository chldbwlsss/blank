package ddobab.blank.web.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {

    private String nickname;
//    private String profileImgUrl;

    @Builder
    public UserUpdateRequestDto(String nickname) {
        this.nickname = nickname;
    }
}
