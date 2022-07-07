package ddobab.blank.service.question;

import ddobab.blank.domain.answer.Answer;
import ddobab.blank.domain.answer.AnswerRepository;
import ddobab.blank.domain.question.Question;
import ddobab.blank.domain.question.QuestionCategory;
import ddobab.blank.domain.question.QuestionRepository;
import ddobab.blank.domain.user.Role;
import ddobab.blank.domain.user.User;
import ddobab.blank.domain.user.UserRepository;
import ddobab.blank.web.dto.QuestionRequestDto;
import ddobab.blank.web.dto.QuestionResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private QuestionService questionService;

    @Test
    void 질문_저장() {
        //given
        QuestionRequestDto questionRequestDto = QuestionRequestDto.builder()
                .content("저장요청질문")
                .categoryValue("ART")
                .build();

        User user = new User(1L, "닉네임", "test@gmail.com"
                                ,"testProfileImgUrl", Role.USER);

        Question toSaveQuestion = Question.builder()
                                .content(questionRequestDto.getContent())
                                .category(QuestionCategory.valueOf(questionRequestDto.getCategoryValue()))
                                .user(user)
                                .views(0)
                                .build();

        Question savedQuestion = new Question(1L, user, toSaveQuestion.getContent()
                                    , toSaveQuestion.getViews(), toSaveQuestion.getCategory());

        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(questionRepository.save(any(Question.class))).willReturn(savedQuestion);

        //when
        QuestionResponseDto questionResponseDto = questionService.save(1L, questionRequestDto);

        //then
        assertThat(questionResponseDto.getContent()).isEqualTo(questionRequestDto.getContent());
        assertThat(questionResponseDto.getCategoryValue()).isEqualTo(QuestionCategory.valueOf(questionRequestDto.getCategoryValue()).getKorValue());
    }

    @Test
    void 질문_조회() {
        User user = new User(1L, "닉네임", "test@gmail.com"
                ,"testProfileImgUrl", Role.USER);

        Question questionEntity = new Question(1L, user, "test 내용", 0, QuestionCategory.ART);

        given(questionRepository.findById(1L)).willReturn(Optional.of(questionEntity));

        //when
        QuestionResponseDto questionResponseDto = questionService.findByNo(1L);

        assertThat(questionResponseDto.getWriter()).isEqualTo(questionEntity.getUser().getNickname());
        assertThat(questionResponseDto.getContent()).isEqualTo(questionEntity.getContent());
        assertThat(questionResponseDto.getCategoryValue()).isEqualTo(questionEntity.getCategory().getKorValue());
        assertThat(questionResponseDto.getViews()).isEqualTo(1);
    }

    @Test
    void 질문_수정() {
        User user = new User(1L, "닉네임", "test@gmail.com"
                ,"testProfileImgUrl", Role.USER);

        Question questionEntity = new Question(1L, user, "변경 전 질문", 0, QuestionCategory.ART);

        QuestionRequestDto questionRequestDto = QuestionRequestDto.builder()
                                                            .content("변경 후 질문")
                                                            .categoryValue("LIVING")
                                                            .build();

        given(questionRepository.findById(1L)).willReturn(Optional.of(questionEntity));

        QuestionResponseDto questionResponseDto = questionService.update(1L, questionRequestDto);

        assertThat(questionEntity.getContent()).isEqualTo(questionResponseDto.getContent());
        assertThat(questionEntity.getCategory().getKorValue()).isEqualTo(questionResponseDto.getCategoryValue());
    }

    @Test
    void 질문_삭제() {
        User user = new User(1L, "닉네임", "test@gmail.com"
                ,"testProfileImgUrl", Role.USER);

        Question questionEntity = new Question(1L, user, "테스트 질문", 0, QuestionCategory.ART);

        Answer answerEntity = new Answer(1L, "테스트 답변", questionEntity, user);

        willDoNothing().given(answerRepository).deleteByQuestionNo(1L);
        given(questionRepository.findById(1L)).willReturn(Optional.of(questionEntity));
        willDoNothing().given(questionRepository).delete(questionEntity);

        //when
        questionService.delete(1L);
    }
}