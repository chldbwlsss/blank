package ddobab.blank.web;

import ddobab.blank.security.dto.SessionUserDto;
import ddobab.blank.service.answer.AnswerService;
import ddobab.blank.web.dto.AnswerResponseDto;
import ddobab.blank.web.dto.AnswerSaveRequestDto;
import ddobab.blank.web.dto.AnswerUpdateRequestDto;
import ddobab.blank.web.dto.QuestionResponseDto;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<AnswerResponseDto> save(@SessionAttribute(name="loginUser", required = false) SessionUserDto loginUser, @RequestBody AnswerSaveRequestDto requestDto) {
//        requestDto.setUserNo(loginUser.getUserNo());  !!!나중에 주석제거

        AnswerResponseDto savedAnswer = answerService.save(requestDto);
        return new ResponseEntity<>(savedAnswer, HttpStatus.CREATED);
    }

    @GetMapping("/{no}")  //질문번호로 답변리스트 가져옴
    public ResponseEntity<List<AnswerResponseDto>> findByQuestionNo(@PathVariable Long no) {
        return new ResponseEntity<>(answerService.findByQuestionNo(no), HttpStatus.OK);
    }

    @PutMapping("/{no}")
    public ResponseEntity<AnswerResponseDto> update(@PathVariable Long no, @RequestBody AnswerUpdateRequestDto requestDto) {
        return new ResponseEntity<>(answerService.update(no, requestDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{no}")
    public ResponseEntity<Void> delete(@PathVariable Long no) {
        answerService.delete(no);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
