package ddobab.blank.web;

import ddobab.blank.domain.question.QuestionCategory;
import ddobab.blank.security.annotation.LoginUser;
import ddobab.blank.security.dto.SessionUserDto;
import ddobab.blank.service.question.QuestionService;
import ddobab.blank.web.dto.QuestionResponseDto;
import ddobab.blank.web.dto.QuestionSaveRequestDto;
import ddobab.blank.web.dto.QuestionUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/question")
@RestController
public class QuestionApiV1Controller {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<QuestionResponseDto> save(@LoginUser SessionUserDto loginUser, @RequestBody QuestionSaveRequestDto requestDto) {

        QuestionResponseDto savedQuestion = questionService.save(loginUser.getNo(), requestDto);
        log.info("savedQuestion = {}", savedQuestion);
        return new ResponseEntity<>(savedQuestion, HttpStatus.CREATED);
    }

    @GetMapping("/{no}")
    public ResponseEntity<QuestionResponseDto> findByNo(@PathVariable Long no) {
        return new ResponseEntity<>(questionService.findByNo(no), HttpStatus.OK);
    }

    @PutMapping("/{no}")
    public ResponseEntity<QuestionResponseDto> update(@LoginUser SessionUserDto loginUser, @PathVariable Long no, @RequestBody QuestionUpdateRequestDto requestDto) {
        if(no.equals(loginUser.getNo())) {
            return new ResponseEntity<>(questionService.update(no, requestDto), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//예외처리, 메세지 넘기기
        }
    }

    @DeleteMapping("/{no}")
    public ResponseEntity<Void> delete(@LoginUser SessionUserDto loginUser, @PathVariable Long no) {
        if (no.equals(loginUser.getNo())) {
            questionService.delete(no);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);   //예외처리 메세지
        }
    }

    @GetMapping("/category")
    public ResponseEntity<QuestionCategory[]> getCategoryList() {
        return new ResponseEntity<>(QuestionCategory.values(), HttpStatus.OK);
    }

    @GetMapping("/top5")
    public ResponseEntity<List<QuestionResponseDto>> getQuestionTop5() {
        return new ResponseEntity<>(questionService.getTop5ByViews(), HttpStatus.OK);
    }
}
