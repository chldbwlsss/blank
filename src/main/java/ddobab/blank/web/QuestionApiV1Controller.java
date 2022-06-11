package ddobab.blank.web;

import ddobab.blank.domain.question.QuestionCategory;
import ddobab.blank.service.question.QuestionService;
import ddobab.blank.web.dto.QuestionResponseDto;
import ddobab.blank.web.dto.QuestionSaveRequestDto;
import ddobab.blank.web.dto.QuestionUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/question")
@RestController
public class QuestionApiV1Controller {

    private final QuestionService questionService;

    @PostMapping
    public Long save(@RequestBody QuestionSaveRequestDto requestDto) {
        return questionService.save(requestDto);
    }

    @GetMapping("/{no}")
    public QuestionResponseDto findByNo(@PathVariable Long no) {
        return questionService.findByNo(no);
    }

    @PutMapping("/{no}")
    public Long update(@PathVariable Long no, @RequestBody QuestionUpdateRequestDto requestDto) {
        return questionService.update(no, requestDto);
    }

    @GetMapping("/category")
    public QuestionCategory[] getCategoryList() {
        return QuestionCategory.values();
    }
}
