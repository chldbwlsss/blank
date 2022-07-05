package ddobab.blank.web.controller;

import ddobab.blank.exception.customException.UnauthorizedException;
import ddobab.blank.security.annotation.LoginUser;
import ddobab.blank.security.dto.SessionUserDto;
import ddobab.blank.service.answer.AnswerService;
import ddobab.blank.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/answer")
@RestController
public class AnswerApiV1Controller {

    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity<ResponseDto<AnswerResponseDto>> save(@LoginUser SessionUserDto loginUser, @RequestBody AnswerRequestDto requestDto) {
        //bean validation 필요
            AnswerResponseDto data = answerService.save(loginUser.getNo(), requestDto);
            return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.CREATED);
    }

    @GetMapping  //질문번호로 답변리스트 가져옴
    public ResponseEntity<ResponseDto<AnswerSliceResponseDto>> getAnswers(@RequestParam("questionNo") Long questionNo,@RequestParam("page") String page, @RequestParam("size") String size){
       //잘못된 파라미터 요청(bad request), 잘못된 타입 요청
        PageRequest pageRequest = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));
        AnswerSliceResponseDto data = answerService.findAnswers(pageRequest, questionNo);
        return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.OK);
    }

    @PreAuthorize("@webSecurity.checkAnswerAuthority(#no, #loginUser)")
    @PutMapping("/{no}")
    public ResponseEntity<ResponseDto<AnswerResponseDto>> update(@LoginUser SessionUserDto loginUser, @PathVariable Long no, @RequestBody AnswerRequestDto requestDto) {
       //bean validation
        AnswerResponseDto data = answerService.update(no, requestDto);
        return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("@webSecurity.checkAnswerAuthority(#no, #loginUser)")
    @DeleteMapping("/{no}")
    public ResponseEntity<ResponseDto<?>> delete(@LoginUser SessionUserDto loginUser, @PathVariable Long no) {

        answerService.delete(no);
        return new ResponseEntity<>(new ResponseDto<>(null, null), HttpStatus.OK);
    }
}
