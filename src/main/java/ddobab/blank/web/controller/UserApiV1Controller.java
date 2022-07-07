package ddobab.blank.web.controller;


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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<ResponseDto<SessionUserDto>> getSessionUser(@LoginUser SessionUserDto loginUser) {
        if(loginUser!=null){
            log.info("[GET] LOGIN-USER : {}", loginUser);
            return new ResponseEntity<>(new ResponseDto<SessionUserDto>(loginUser,null), HttpStatus.OK);
        }else{
            throw new AccessDeniedException("데이터 접근 권한이 없습니다.");
        }

    }

    @GetMapping("/{no}")
    public ResponseEntity<ResponseDto<UserResponseDto>> getUserProfile(@PathVariable Long no) {
        UserResponseDto data = userService.findByNo(no);
        return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.OK);
    }

    @PreAuthorize("@webSecurity.checkUserAuthority(#no, #loginUser)")
    @PutMapping("/{no}")
    public ResponseEntity<ResponseDto<UserResponseDto>> update(@PathVariable Long no, @RequestBody UserRequestDto requestDto,
                                                  @LoginUser SessionUserDto loginUser) {
        //bean validation 점검
        UserResponseDto data = userService.update(no, requestDto);
        return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("@webSecurity.checkUserAuthority(#no, #loginUser)")
    @DeleteMapping("/{no}")
    public ResponseEntity<ResponseDto<?>> delete(@LoginUser SessionUserDto loginUser, @PathVariable Long no) {
        //잘못된 타입으로 요청
        userService.delete(no);
        return new ResponseEntity<>(new ResponseDto<>(null, null), HttpStatus.OK);
    }

    @GetMapping("/{no}/question/top3")
    public ResponseEntity<ResponseDto<List<QuestionResponseDto>>> getQuestionsTop3(@PathVariable Long no) {
        List<QuestionResponseDto> data = questionService.findTop3ByUserNo(no);
        return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.OK);
    }

    @GetMapping("/{no}/answer/top3")
    public ResponseEntity<ResponseDto<List<AnswerResponseDto>>> getAnswersTop3(@PathVariable Long no) {
        List<AnswerResponseDto> data = answerService.findTop3ByUserNo(no);
        return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.OK);
    }
}
