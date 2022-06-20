package ddobab.blank.web;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/v1/user")
@RestController
public class UserApiV1Controller {

    @GetMapping
    public ResponseEntity<SessionUserDto> getSessionUser(@CookieValue("JSESSIONID") String sessionId, @SessionAttribute(name="loginUser", required = false) SessionUserDto loginUser) {
        log.info("[GET] LOGIN-USER : {}", loginUser);
        log.info("SESSEION-ID : {}", sessionId);
        return new ResponseEntity<>(loginUser, loginUser!=null?HttpStatus.OK:HttpStatus.NO_CONTENT);
    }

}
