package ddobab.blank.web;

import ddobab.blank.service.search.SearchService;
import ddobab.blank.web.dto.QuestionResponseDto;
import ddobab.blank.web.dto.QuestionSliceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
@RestController
public class SearchApiV1Controller {

    private final SearchService searchService;

    @GetMapping("/question")
    public ResponseEntity<List<QuestionResponseDto>> searchQuestion(@RequestParam("categoryValue") String categoryValue,
                                                                    @RequestParam("word") String word) {
        return new ResponseEntity<>(searchService.getSearchedQuestion(categoryValue, word), HttpStatus.OK);
    }

    @GetMapping("/question/slice")
    public ResponseEntity<QuestionSliceResponseDto> getQuestionSlice(@RequestParam("categoryValue") String categoryValue, @RequestParam("word") String word,
                                                                            @RequestParam("page") String page, @RequestParam("size") String size) {
        PageRequest pageRequest = PageRequest.of(Integer.parseInt(page),Integer.parseInt(size));
        return new ResponseEntity<>(searchService.getSearchedQuestionSlice(pageRequest, categoryValue, word), HttpStatus.OK);
    }
}
