package ddobab.blank.web;

import ddobab.blank.service.question.QuestionService;
import ddobab.blank.web.dto.QuestionResponseDto;
import ddobab.blank.web.dto.QuestionSaveRequestDto;
import ddobab.blank.web.dto.QuestionUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class QuestionApiController {

    private final QuestionService questionService;

    @PostMapping("/api/question")
    public Long save(@RequestBody QuestionSaveRequestDto requestDto) {
        return questionService.save(requestDto);
    }

    @PutMapping("/api/question/{no}")
    public Long update(@PathVariable Long no, @RequestBody QuestionUpdateRequestDto requestDto) {
        return questionService.update(no, requestDto);
    }

    @GetMapping("/api/question/{no}")
    public QuestionResponseDto findByNo(@PathVariable Long no) {
        return questionService.findByNo(no);
    }
}
