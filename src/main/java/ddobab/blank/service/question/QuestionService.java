package ddobab.blank.service.question;

import ddobab.blank.domain.answer.AnswerRepository;
import ddobab.blank.domain.question.*;
import ddobab.blank.domain.user.UserRepository;
import ddobab.blank.web.dto.QuestionRequestDto;
import ddobab.blank.web.dto.QuestionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionImgRepository questionImgRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    @Transactional
    public QuestionResponseDto save(Long userNo, QuestionRequestDto requestDto) {
        Question toSaveQuestion = Question.builder()
                                            .user(userRepository.findById(userNo).orElseThrow(() -> new NoSuchElementException("해당 사용자를 찾을 수 없습니다. USER-NO:"+userNo)))
                                            .content(requestDto.getContent())
                                            .category(QuestionCategory.valueOf(requestDto.getCategoryValue()))
                                            .views(0)
                                            .build();
        Question savedQuestion = questionRepository.save(toSaveQuestion);
        //질문이미지 저장해야됨!!!
        return new QuestionResponseDto(savedQuestion);

    }

    @Transactional
    public QuestionResponseDto findByNo(Long no) {
        Question question = questionRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문을 찾을 수 없습니다. QUESTION-NO:"+no));

        question.plusViews();

        return new QuestionResponseDto(question);
    }

    @Transactional
    public QuestionResponseDto update(Long no, QuestionRequestDto requestDto) {
        Question question = questionRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문을 찾을 수 없습니다. QUESTION-NO:"+no));
        question.updateQuestion(requestDto.getContent(), QuestionCategory.valueOf(requestDto.getCategoryValue()));

//        questionImgRepository.deleteByQuestionNo(no);
//        requestDto.getQuestionImgUrls()
//                .stream()
//                .map(imgUrl -> questionImgRepository.save(QuestionImg.builder()
//                        .question(question)
//                        .questionImgUrl(imgUrl)
//                        .build()));     !!!나중에 주석 제거
        return new QuestionResponseDto(question);
    }

    @Transactional
    public void delete(Long no) {
        answerRepository.deleteByQuestionNo(no);
        questionRepository.delete(questionRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문을 찾을 수 없습니다. QUESTION-NO:"+no)));

    }

    public List<QuestionResponseDto> findTop3ByUserNo(Long no) {
        List<Question> questionTop3List = questionRepository.findTop3ByUserNoOrderByCreatedDateDesc(no);
        return questionTop3List.stream()
                                .map(QuestionResponseDto::new)
                                .collect(Collectors.toList());
    }

    public List<QuestionResponseDto> getTop5ByViews() {
        LocalDateTime twoDaysAgo = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.of(0,0,0));

        List<Question> questionTop5List = questionRepository.findTop5ByCreatedDateGreaterThanOrderByViewsDesc(twoDaysAgo);
        return questionTop5List.stream()
                                .map(QuestionResponseDto::new)
                                .collect(Collectors.toList());
    }

    public Long getQuestionWriter(Long no) {
        Question question = questionRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문을 찾을 수 없습니다. QUESTION-NO:"+no));
        return question.getUser().getNo();
    }
}
