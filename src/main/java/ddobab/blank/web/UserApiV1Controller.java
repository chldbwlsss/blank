package ddobab.blank.web;


import ddobab.blank.security.dto.SessionUserDto;
import ddobab.blank.service.answer.AnswerService;
import ddobab.blank.service.question.QuestionService;
import ddobab.blank.service.user.UserService;
import ddobab.blank.web.dto.AnswerResponseDto;
import ddobab.blank.web.dto.QuestionResponseDto;
import ddobab.blank.web.dto.UserResponseDto;
import ddobab.blank.web.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class UserApiV1Controller {

    private final UserService userService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping
    public ResponseEntity<SessionUserDto> getSessionUser(@SessionAttribute(name = "loginUser", required = false) SessionUserDto loginUser) {
        log.info("[GET] LOGIN-USER : {}", loginUser);

        return new ResponseEntity<>(loginUser, loginUser != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{no}")
    public ResponseEntity<UserResponseDto> getUserProfile(@PathVariable Long no) {
        return new ResponseEntity<>(userService.findByNo(no), HttpStatus.OK);
    }

    @PutMapping("/{no}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long no, @RequestBody UserUpdateRequestDto requestDto,
                                                  @SessionAttribute(name = "loginUser", required = false) SessionUserDto loginUser) {
        if(no.equals(loginUser.getNo())) {
            return new ResponseEntity<>(userService.update(no, requestDto), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{no}/delete")
    public ResponseEntity<?> delete(@PathVariable Long no) {
        userService.delete(no);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{no}/question/top3")
    public ResponseEntity<List<QuestionResponseDto>> getQuestionTop3(@PathVariable Long no) {
        return new ResponseEntity<>(questionService.findTop3ByUserNo(no), HttpStatus.OK);
    }

    @GetMapping("/{no}/answer/top3")
    public ResponseEntity<List<AnswerResponseDto>> getAnswerTop3(@PathVariable Long no) {
        return new ResponseEntity<>(answerService.findTop3ByUserNo(no), HttpStatus.OK);
    }
}
