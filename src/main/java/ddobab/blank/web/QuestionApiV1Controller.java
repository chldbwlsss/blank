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

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/question")
@RestController
public class QuestionApiV1Controller {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<QuestionResponseDto> save(@SessionAttribute(name="loginUser", required = false) SessionUserDto loginUser, @RequestBody QuestionSaveRequestDto requestDto) {
        requestDto.setUserNo(loginUser.getNo());

        QuestionResponseDto savedQuestion = questionService.save(requestDto);
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
    }

    @DeleteMapping("/{no}")
    public ResponseEntity<Void> delete(@PathVariable Long no) {
        questionService.delete(no);
         return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<QuestionCategory[]> getCategoryList() {
        return new ResponseEntity<>(QuestionCategory.values(), HttpStatus.OK);
    }
}
