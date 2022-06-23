package ddobab.blank.web;


import ddobab.blank.security.dto.SessionUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/v1/user")
@RestController
public class UserApiV1Controller {

    @GetMapping
    public ResponseEntity<SessionUserDto> getSessionUser(@SessionAttribute(name="loginUser", required = false) SessionUserDto loginUser) {
        log.info("[GET] LOGIN-USER : {}", loginUser);
        log.info("SESSEION-ID : {}", sessionId);

        return new ResponseEntity<>(loginUser, loginUser!=null? HttpStatus.OK:HttpStatus.NO_CONTENT);
    }
}
