package ddobab.blank.service.search;

import ddobab.blank.domain.question.Question;
import ddobab.blank.domain.question.QuestionCategory;
import ddobab.blank.domain.question.QuestionRepository;
import ddobab.blank.domain.user.User;
import ddobab.blank.web.dto.QuestionSliceResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private SearchService searchService;

    @Test
    void 검색된_질문_조회() {
        //given
        PageRequest pageRequest = PageRequest.of(1, 5);
        List<Question> questions = new ArrayList<>();
        Question question = new Question(1L, new User(), "테스트질문", 3, QuestionCategory.ECONOMY);

        questions.add(question);
        SliceImpl<Question> slice = new SliceImpl<>(questions, Pageable.ofSize(5), true);

        given(questionRepository.findByCategoryAndContentContainingIgnoreCaseOrderByCreatedDateDesc(eq(QuestionCategory.ECONOMY), eq("질문"), eq(pageRequest))).willReturn(slice);

        //when
        QuestionSliceResponseDto questionSliceResponseDto = searchService.findSearchedQuestions(pageRequest, QuestionCategory.ECONOMY.toString(), "질문");

        //then
        assertThat(questionSliceResponseDto.getQuestions()).hasSize(1);
        assertThat(questionSliceResponseDto.getQuestions().get(0).getContent()).isEqualTo(question.getContent());

    }
}
