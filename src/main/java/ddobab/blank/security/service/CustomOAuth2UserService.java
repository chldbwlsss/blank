package ddobab.blank.security.service;

import ddobab.blank.security.oauth2.dto.OAuthAttributesDto;
import ddobab.blank.security.dto.SessionUserDto;
import ddobab.blank.domain.user.User;
import ddobab.blank.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributesDto oAuthAttributesDto = OAuthAttributesDto.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = save(oAuthAttributesDto);
        httpSession.setAttribute("loginUser", new SessionUserDto(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                oAuthAttributesDto.getAttributes(),
                oAuthAttributesDto.getNameAttributeName()
        );
    }

    private User save(OAuthAttributesDto oAuthAttributesDto){

        User user = userRepository.findByEmail(oAuthAttributesDto.getEmail()).orElse(oAuthAttributesDto.toEntity());
        return userRepository.save(user);
    }
}
