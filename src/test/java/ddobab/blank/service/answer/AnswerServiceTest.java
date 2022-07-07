package ddobab.blank.service.answer;

import ddobab.blank.domain.answer.Answer;
import ddobab.blank.domain.answer.AnswerRepository;
import ddobab.blank.domain.question.Question;
import ddobab.blank.domain.question.QuestionCategory;
import ddobab.blank.domain.question.QuestionRepository;
import ddobab.blank.domain.user.Role;
import ddobab.blank.domain.user.User;
import ddobab.blank.domain.user.UserRepository;
import ddobab.blank.web.dto.AnswerRequestDto;
import ddobab.blank.web.dto.AnswerResponseDto;
import ddobab.blank.web.dto.AnswerSliceResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AnswerServiceTest {

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private AnswerService answerService;

    @Test
    void 답변_저장(){
        //given
        AnswerRequestDto answerRequestDto = AnswerRequestDto.builder()
                .questionNo(1L)
                .content("테스트내용")
                .build();
        User user = new User(4L, "테스트유저", "test-email", "test-img", Role.USER);
        Question question = new Question(1L, user, "테스트질문내용", 3, QuestionCategory.ART);

        Answer toSaveAnswer = Answer.builder()
                .user(user)
                .question(question)
                .content(answerRequestDto.getContent())
                .build();
        Answer savedAnswer = new Answer(1L, answerRequestDto.getContent(), question, user);
        given(userRepository.findById(4L)).willReturn(Optional.of(user));
        given(questionRepository.findById(1L)).willReturn(Optional.of(question));
        given(answerRepository.save(eq(toSaveAnswer))).willReturn(savedAnswer);

        //when
        AnswerResponseDto answerResponseDto = answerService.save(4L, answerRequestDto);

        //then
        assertThat(answerResponseDto.getWriterNo()).isEqualTo(4L);
        assertThat(answerResponseDto.getContent()).isEqualTo(answerRequestDto.getContent());
    }

    @Test
    void 질문번호로_답변목록_조회(){
        //given
        PageRequest pageRequest = PageRequest.of(1, 3);
        List<Answer> answers = new ArrayList<>();
        Answer answer = new Answer(1L, "테스트답변내용", new Question(), new User());
        answers.add(answer);
        SliceImpl<Answer> slice = new SliceImpl<>(answers, Pageable.ofSize(3), true);
        given(answerRepository.findByQuestionNoOrderByCreatedDateDesc(eq(1L), eq(pageRequest))).willReturn(slice);

        //when
        AnswerSliceResponseDto answerSliceResponseDto = answerService.findAnswers(pageRequest, 1L);

        //then
        assertThat(answerSliceResponseDto.getAnswers()).hasSize(1);
        assertThat(answerSliceResponseDto.isHasNext()).isEqualTo(true);
    }

    @Test
    void 답변_수정(){
        //given
        AnswerRequestDto answerRequestDto = AnswerRequestDto.builder()
                .questionNo(2L)
                .content("테스트내용 변경됨")
                .build();
        Answer answer = new Answer(1L, "테스트내용", new Question(), new User());
        given(answerRepository.findById(1L)).willReturn(Optional.of(answer));

        //when
        AnswerResponseDto answerResponseDto = answerService.update(1L, answerRequestDto);

        //then
        assertThat(answerResponseDto.getNo()).isEqualTo(answer.getNo());
        assertThat(answerResponseDto.getContent()).isEqualTo(answerRequestDto.getContent());
    }

    @Test
    void 답변_삭제(){
        //given
        Answer answer = new Answer(1L, "테스트내용", new Question(), new User());
        given(answerRepository.findById(1L)).willReturn(Optional.of(answer));
        willDoNothing().given(answerRepository).delete(eq(answer));
        //when, then
        answerService.delete(1L);
    }
}