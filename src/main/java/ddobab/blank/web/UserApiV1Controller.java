package ddobab.blank.web;


import ddobab.blank.security.annotation.LoginUser;
import ddobab.blank.security.dto.SessionUserDto;
import ddobab.blank.service.answer.AnswerService;
import ddobab.blank.service.question.QuestionService;
import ddobab.blank.service.user.UserService;
import ddobab.blank.web.dto.*;
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
    public ResponseEntity<SessionUserDto> getSessionUser(@LoginUser SessionUserDto loginUser) {
        log.info("[GET] LOGIN-USER : {}", loginUser);

        return new ResponseEntity<>(loginUser, loginUser != null ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{no}")
    public ResponseEntity<ResponseDto<UserResponseDto>> getUserProfile(@PathVariable Long no) {
        //잘못된 타입으로 요청
        UserResponseDto data = userService.findByNo(no);
        return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.OK);
    }

    @PutMapping("/{no}")
    public ResponseEntity<ResponseDto<UserResponseDto>> update(@PathVariable Long no, @RequestBody UserUpdateRequestDto requestDto,
                                                  @LoginUser SessionUserDto loginUser) {
        //bean validation 점검
        if(no.equals(loginUser.getNo())) {
            UserResponseDto data = userService.update(no, requestDto);
            return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{no}/delete")
    public ResponseEntity<ResponseDto<?>> delete(@LoginUser SessionUserDto loginUser, @PathVariable Long no) {
        //잘못된 타입으로 요청
        if(no.equals(loginUser.getNo())) {
            userService.delete(no);
            return new ResponseEntity<>(new ResponseDto<>(null, null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{no}/question/top3")
    public ResponseEntity<ResponseDto<List<QuestionResponseDto>>> getQuestionsTop3(@PathVariable Long no) {
        //잘못된 타입으로 요청
        List<QuestionResponseDto> data = questionService.findTop3ByUserNo(no);
        return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.OK);
    }

    @GetMapping("/{no}/answer/top3")
    public ResponseEntity<ResponseDto<List<AnswerResponseDto>>> getAnswersTop3(@PathVariable Long no) {
        //잘못된 타입으로 요청
        List<AnswerResponseDto> data = answerService.findTop3ByUserNo(no);
        return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.OK);
    }
}
