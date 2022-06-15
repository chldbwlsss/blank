package ddobab.blank.config.auth.dto;

import ddobab.blank.domain.user.Role;
import ddobab.blank.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributesDto {

    private Map<String, Object> attributes;
    private String nameAttributeName;
    private String nickname;
    private String email;
    private String profileImgUrl;

    @Builder
    public OAuthAttributesDto(Map<String, Object> attributes, String nameAttributeName, String nickname, String email, String profileImgUrl) {
        this.attributes = attributes;
        this.nameAttributeName = nameAttributeName;
        this.nickname = nickname;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
    }

    public static OAuthAttributesDto of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("google".equals(registrationId)) {
            return ofGoogle(userNameAttributeName, attributes);
        }
        return null;
    }

    private static OAuthAttributesDto ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributesDto.builder()
                .nickname((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profileImgUrl((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeName(userNameAttributeName)
                .build();

    }

    public User toEntity(){
        return User.builder()
                .nickname(nickname)
                .email(email)
                .profileImgUrl(profileImgUrl)
                .role(Role.USER)
                .build();
    }
}
