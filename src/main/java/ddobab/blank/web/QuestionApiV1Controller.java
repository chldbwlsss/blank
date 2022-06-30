package ddobab.blank.web;

import ddobab.blank.domain.question.QuestionCategory;
import ddobab.blank.security.annotation.LoginUser;
import ddobab.blank.security.dto.SessionUserDto;
import ddobab.blank.service.question.QuestionService;
import ddobab.blank.web.dto.QuestionResponseDto;
import ddobab.blank.web.dto.QuestionSaveRequestDto;
import ddobab.blank.web.dto.QuestionUpdateRequestDto;
import ddobab.blank.web.dto.ResponseDto;
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
    public ResponseEntity<ResponseDto<QuestionResponseDto>> save(@LoginUser SessionUserDto loginUser, @RequestBody QuestionSaveRequestDto requestDto) {
        //bean validation 필요
        QuestionResponseDto data = questionService.save(loginUser.getNo(), requestDto);
        //log 필요
        return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.CREATED);
    }

    @GetMapping("/{no}")
    public ResponseEntity<ResponseDto<QuestionResponseDto>> getQuestion(@PathVariable Long no) {
        //pathvariable type mismatch? ex
        QuestionResponseDto data = questionService.findByNo(no);

        return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.OK);
    }

    @PutMapping("/{no}")
    public ResponseEntity<ResponseDto<QuestionResponseDto>> update(@LoginUser SessionUserDto loginUser, @PathVariable Long no, @RequestBody QuestionUpdateRequestDto requestDto) {
        //bean valid
        Long writerNo = questionService.getQuestionWrtier(no);
        if(writerNo.equals(loginUser.getNo())) {
            QuestionResponseDto data = questionService.update(no, requestDto);
            return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//예외처리, 메세지 넘기기
        }
    }

    @DeleteMapping("/{no}")
    public ResponseEntity<ResponseDto<?>> delete(@LoginUser SessionUserDto loginUser, @PathVariable Long no) {
        //path var ex
        Long writerNo = questionService.getQuestionWrtier(no);
        if (writerNo.equals(loginUser.getNo())) {
            questionService.delete(no);
            return new ResponseEntity<>(new ResponseDto<>(null, null),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);   //예외처리 메세지
        }
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
