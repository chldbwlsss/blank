package ddobab.blank.security.controller;

import ddobab.blank.security.dto.SessionUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@RequestMapping("/api/v1/login")
@Controller
public class LoginController {

    @GetMapping
    public ResponseEntity<SessionUserDto> getSessionUser(@SessionAttribute(name="loginUser", required = false) SessionUserDto loginUser) {
        log.info("[GET] LOGIN-USER : {}", loginUser);

        return new ResponseEntity<>(loginUser, loginUser!=null?HttpStatus.OK:HttpStatus.NO_CONTENT);
    }
}
