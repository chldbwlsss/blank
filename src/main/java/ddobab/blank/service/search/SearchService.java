package ddobab.blank.service.search;


import ddobab.blank.domain.question.Question;
import ddobab.blank.domain.question.QuestionCategory;
import ddobab.blank.domain.question.QuestionRepository;
import ddobab.blank.web.dto.QuestionResponseDto;
import ddobab.blank.web.dto.QuestionSliceResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchService {

    private final QuestionRepository questionRepository;

    public QuestionSliceResponseDto findSearchedQuestions(PageRequest pageRequest, String categoryValue, String word) {
        Slice<Question> slice;

        if ("NONE".equals(categoryValue)) {
            slice = questionRepository.findByContentContainingIgnoreCaseOrderByCreatedDateDesc(word, pageRequest);
        } else {
            slice = questionRepository.findByCategoryAndContentContainingIgnoreCaseOrderByCreatedDateDesc(QuestionCategory.valueOf(categoryValue), word, pageRequest);
        }
        List<QuestionResponseDto> content = slice.getContent().stream()
                                                                .map(QuestionResponseDto::new)
                                                                .collect(Collectors.toList());
        log.info("[SEARCH QUESTIONS] word:{}, category:{}, pageNo: {}", word, categoryValue, pageRequest.getPageNumber());
        return new QuestionSliceResponseDto(content, slice.hasNext());
    }
}
