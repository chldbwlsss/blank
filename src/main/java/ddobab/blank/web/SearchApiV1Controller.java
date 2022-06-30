package ddobab.blank.web;

import ddobab.blank.service.search.SearchService;
import ddobab.blank.web.dto.QuestionSliceResponseDto;
import ddobab.blank.web.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
@RestController
public class SearchApiV1Controller {

    private final SearchService searchService;

    @GetMapping("/question")
    public ResponseEntity<ResponseDto<QuestionSliceResponseDto>> getQuestions(@RequestParam("categoryValue") String categoryValue, @RequestParam("word") String word,
                                                                            @RequestParam("page") String page, @RequestParam("size") String size) {
        //잘못된 파라미터 요청(bad request)
        PageRequest pageRequest = PageRequest.of(Integer.parseInt(page),Integer.parseInt(size));
        QuestionSliceResponseDto data = searchService.findSearchedQuestions(pageRequest, categoryValue, word);
        return new ResponseEntity<>(new ResponseDto<>(data, null), HttpStatus.OK);

    }
}
