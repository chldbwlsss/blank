package ddobab.blank.web;

import ddobab.blank.service.search.SearchService;
import ddobab.blank.web.dto.QuestionResponseDto;
import ddobab.blank.web.dto.SearchRequestDto;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<QuestionResponseDto>> searchQuestion(@RequestBody SearchRequestDto requestDto) {
        return new ResponseEntity<>(searchService.getSearchedQuestion(requestDto), HttpStatus.OK);
    }

}
