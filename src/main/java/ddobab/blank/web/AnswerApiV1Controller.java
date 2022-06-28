package ddobab.blank.web;

import ddobab.blank.security.annotation.LoginUser;
import ddobab.blank.security.dto.SessionUserDto;
import ddobab.blank.service.answer.AnswerService;
import ddobab.blank.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/answer")
@RestController
public class AnswerApiV1Controller {

    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity<AnswerResponseDto> save(@LoginUser SessionUserDto loginUser, @RequestBody AnswerSaveRequestDto requestDto) {

        AnswerResponseDto savedAnswer = answerService.save(loginUser.getNo(), requestDto);
        return new ResponseEntity<>(savedAnswer, HttpStatus.CREATED);
    }

    @GetMapping  //질문번호로 답변리스트 가져옴
    public ResponseEntity<AnswerSliceResponseDto> findAnswers(@RequestParam("questionNo") Long questionNo,@RequestParam("page") String page, @RequestParam("size") String size){
        PageRequest pageRequest = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));

        return new ResponseEntity<>(answerService.findAnswers(pageRequest, questionNo), HttpStatus.OK);
    }

    @PutMapping("/{no}")
    public ResponseEntity<AnswerResponseDto> update(@LoginUser SessionUserDto loginUser, @PathVariable Long no, @RequestBody AnswerUpdateRequestDto requestDto) {
        if(no.equals(loginUser.getNo())) {
            return new ResponseEntity<>(answerService.update(no, requestDto), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  //예외처리, 메세지
        }

    }

    @DeleteMapping("/{no}")
    public ResponseEntity<Void> delete(@LoginUser SessionUserDto loginUser, @PathVariable Long no) {
        if (no.equals(loginUser.getNo())) {
            answerService.delete(no);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); //예외처리, 메세지
        }
    }
}
