package ddobab.blank.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "닉네임은 빈 칸일 수 없습니다.")
//    @Length(max = 20, message = "닉네임은 최대 20자까지 허용됩니다.")
//    @Pattern(regexp = "^[0-9a-zA-Z가-힣]*$")
    private String nickname;
//    private String profileImgUrl;

    @Builder
    public UserRequestDto(String nickname) {
        this.nickname = nickname;
    }
}
