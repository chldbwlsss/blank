package ddobab.blank.web.controller;

import ddobab.blank.domain.question.QuestionCategory;
import ddobab.blank.security.annotation.LoginUser;
import ddobab.blank.security.dto.SessionUserDto;
import ddobab.blank.service.question.QuestionService;
import ddobab.blank.web.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/question")
@RestController
public class QuestionApiV1Controller {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<ResponseDto<QuestionResponseDto>> save(@LoginUser SessionUserDto loginUser, @RequestBody QuestionRequestDto requestDto) {
        //bean validation 필요
        QuestionResponseDto data = questionService.save(loginUser.getNo(), requestDto);
        return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.CREATED);
    }

    @GetMapping("/{no}")
    public ResponseEntity<ResponseDto<QuestionResponseDto>> getQuestion(@PathVariable Long no) {
        QuestionResponseDto data = questionService.findByNo(no);
        return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.OK);
    }

    @PreAuthorize("@webSecurity.checkQuestionAuthority(#no, #loginUser)")
    @PutMapping("/{no}")
    public ResponseEntity<ResponseDto<QuestionResponseDto>> update(@LoginUser SessionUserDto loginUser, @PathVariable Long no, @RequestBody QuestionRequestDto requestDto) {
        //bean valid
        QuestionResponseDto data = questionService.update(no, requestDto);
        return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("@webSecurity.checkQuestionAuthority(#no, #loginUser)")
    @DeleteMapping("/{no}")
    public ResponseEntity<ResponseDto<?>> delete(@LoginUser SessionUserDto loginUser, @PathVariable Long no) {

        questionService.delete(no);
        return new ResponseEntity<>(new ResponseDto<>(null, null),HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<ResponseDto<QuestionCategory[]>> getCategoryList() {
        QuestionCategory[] data = QuestionCategory.values();
        return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.OK);
    }

    @GetMapping("/top5")
    public ResponseEntity<ResponseDto<List<QuestionResponseDto>>> getQuestionsTop5() {
        List<QuestionResponseDto> data = questionService.getTop5ByViews();
        return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.OK);
    }
}
