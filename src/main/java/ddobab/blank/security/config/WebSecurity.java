package ddobab.blank.security.config;

import ddobab.blank.security.dto.SessionUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("webSecurity")
public class WebSecurity {
    public boolean checkAuthority(Long userNo, SessionUserDto loginUser){
        log.info("USER-NO:{}, LOGIN-USER:{}",userNo, loginUser);
        return userNo.equals(loginUser.getNo());
    }
}
