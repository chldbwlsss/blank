package ddobab.blank.service.question;

import ddobab.blank.domain.answer.AnswerRepository;
import ddobab.blank.domain.question.*;
import ddobab.blank.domain.user.UserRepository;
import ddobab.blank.web.dto.QuestionResponseDto;
import ddobab.blank.web.dto.QuestionSaveRequestDto;
import ddobab.blank.web.dto.QuestionUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionImgRepository questionImgRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    @Transactional
    public QuestionResponseDto save(QuestionSaveRequestDto requestDto) {
        Question toSaveQuestion = Question.builder()
                                            .user(userRepository.findById(requestDto.getUserNo()).get())
                                            .content(requestDto.getContent())
                                            .category(QuestionCategory.valueOf(requestDto.getCategoryValue()))
                                            .views(0)
                                            .build();
        Question savedQuestion = questionRepository.save(toSaveQuestion);
        //질문이미지 저장해야됨!!!
        return new QuestionResponseDto(questionRepository.findById(savedQuestion.getNo()).get());

    }

    @Transactional
    public QuestionResponseDto findByNo(Long no) {
        Question question = questionRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문을 찾을 수 없습니다."));

        question.plusViews();

        return new QuestionResponseDto(question);
    }

    @Transactional
    public QuestionResponseDto update(Long no, QuestionUpdateRequestDto requestDto) {
        Question question = questionRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문을 찾을 수 없습니다."));
        question.updateQuestion(requestDto.getContent(), QuestionCategory.valueOf(requestDto.getCategoryValue()));

//        questionImgRepository.deleteByQuestionNo(no);
//        requestDto.getQuestionImgUrls()
//                .stream()
//                .map(imgUrl -> questionImgRepository.save(QuestionImg.builder()
//                        .question(question)
//                        .questionImgUrl(imgUrl)
//                        .build()));     !!!나중에 주석 제거

        return new QuestionResponseDto(questionRepository.findById(no).get());
    }

    @Transactional
    public void delete(Long no) {
        answerRepository.deleteByQuestionNo(no);
        questionRepository.delete(questionRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문입니다.")));

    }


    public List<QuestionResponseDto> findAllByUserNo(Long no) {
        List<Question> questionList = questionRepository.findByUserNo(no);
        List<QuestionResponseDto> responseDtoList = questionList.stream()
                                                            .map(question -> new QuestionResponseDto(question))
                                            .collect(Collectors.toList());

        return responseDtoList;
    }

    public List<QuestionResponseDto> findTop3ByUserNo(Long no) {
        List<Question> questionTop3List = questionRepository.findTop3ByUserNoOrderByCreatedDateDesc(no);
        List<QuestionResponseDto> top3ResponseDtoList = questionTop3List.stream()
                                                                .map(question -> new QuestionResponseDto(question))
                                             .collect(Collectors.toList());
        return top3ResponseDtoList;
    }

    public List<QuestionResponseDto> getTop5ByViews() {
        LocalDateTime twoDaysAgo = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.of(0,0,0));

        List<Question> questionTop5List = questionRepository.findTop5ByCreatedDateGreaterThanOrderByViewsDesc(twoDaysAgo);
        List<QuestionResponseDto> top5ResponseDtoList = questionTop5List.stream()
                                                                .map(question -> new QuestionResponseDto(question))
                                             .collect(Collectors.toList());
        return top5ResponseDtoList;
    }
}
