package ddobab.blank.web;

import ddobab.blank.domain.answer.Answer;
import ddobab.blank.service.answer.AnswerService;
import ddobab.blank.web.dto.AnswerResponseDto;
import ddobab.blank.web.dto.AnswerSaveRequestDto;
import ddobab.blank.web.dto.AnswerUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/answer")
@RestController
public class AnswerApiV1Controller {

    private final AnswerService answerService;

    @PostMapping
    public Long save(@RequestBody AnswerSaveRequestDto requestDto) {
        return answerService.save(requestDto);
    }

    @GetMapping("/{no}")  //질문번호로 답변리스트 가져옴
    public List<AnswerResponseDto> findByQuestionNo(@PathVariable Long no) {
        return answerService.findByQuestionNo(no);
    }

    @PutMapping("/{no}")
    public Long update(@PathVariable Long no, @RequestBody AnswerUpdateRequestDto requestDto) {
        return answerService.update(no, requestDto);
    }

    @DeleteMapping("/{no}")
    public void delete(@PathVariable Long no) {
        answerService.delete(no);
    }
}
