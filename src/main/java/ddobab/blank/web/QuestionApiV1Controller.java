package ddobab.blank.web;

import ddobab.blank.domain.question.QuestionCategory;
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
    public ResponseEntity<QuestionResponseDto> save(@SessionAttribute(name = "loginUser", required = false) SessionUserDto loginUser, @RequestBody QuestionSaveRequestDto requestDto) {

        QuestionResponseDto savedQuestion = questionService.save(loginUser.getNo(), requestDto);
        log.info("savedQuestion = {}", savedQuestion);
        return new ResponseEntity<>(savedQuestion, HttpStatus.CREATED);
    }

    @GetMapping("/{no}")
    public ResponseEntity<QuestionResponseDto> findByNo(@PathVariable Long no) {
        return new ResponseEntity<>(questionService.findByNo(no), HttpStatus.OK);
    }

    @PutMapping("/{no}")
    public ResponseEntity<QuestionResponseDto> update(@PathVariable Long no, @RequestBody QuestionUpdateRequestDto requestDto) {
        return new ResponseEntity<>(questionService.update(no, requestDto), HttpStatus.ACCEPTED);
        //수정 시 변경하려는 글의 작성자와 현재 로그인한 세션유저가 같은지 검증해야함
    }

    @DeleteMapping("/{no}")
    public ResponseEntity<Void> delete(@PathVariable Long no) {
        questionService.delete(no);
        return new ResponseEntity<>(HttpStatus.OK);
        //삭제 시 지우려는 글의 작성자와 현재 로그인한 세션유저가 같은지 검증해야함
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
