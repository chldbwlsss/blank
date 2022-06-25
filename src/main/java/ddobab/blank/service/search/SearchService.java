package ddobab.blank.service.search;


import ddobab.blank.domain.question.Question;
import ddobab.blank.domain.question.QuestionCategory;
import ddobab.blank.domain.question.QuestionRepository;
import ddobab.blank.web.dto.QuestionResponseDto;
import ddobab.blank.web.dto.SearchRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchService {

    private final QuestionRepository questionRepository;

    public List<QuestionResponseDto> getSearchedQuestion(SearchRequestDto requestDto) {
        log.info("requestDto = {}", requestDto);

        if (requestDto.getCategory().equals("NONE")) {
            List<Question> searchedList = questionRepository.findByContentContainingIgnoreCaseOrderByCreatedDateDesc(requestDto.getWord());
            List<QuestionResponseDto> responseDtoList = searchedList.stream()
                                                         .map(question -> new QuestionResponseDto(question))
                                                .collect(Collectors.toList());
            return responseDtoList;
        } else {
            List<Question> searchedList = questionRepository.findByCategoryAndContentContainingIgnoreCaseOrderByCreatedDateDesc(QuestionCategory.valueOf(requestDto.getCategory()), requestDto.getWord());
            List<QuestionResponseDto> responseDtoList = searchedList.stream()
                                                        .map(question -> new QuestionResponseDto(question))
                                                 .collect(Collectors.toList());
            log.info("responseDtoList = {}", responseDtoList);
            return responseDtoList;
        }
    }
}
