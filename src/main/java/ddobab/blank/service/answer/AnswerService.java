package ddobab.blank.service.answer;

import ddobab.blank.domain.answer.Answer;
import ddobab.blank.domain.answer.AnswerImgRepository;
import ddobab.blank.domain.answer.AnswerRepository;
import ddobab.blank.domain.question.QuestionRepository;
import ddobab.blank.domain.user.UserRepository;
import ddobab.blank.web.dto.AnswerResponseDto;
import ddobab.blank.web.dto.AnswerRequestDto;
import ddobab.blank.web.dto.AnswerSliceResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerImgRepository answerImgRepository;

    @Transactional
    public AnswerResponseDto save(Long userNo, AnswerRequestDto requestDto) {
        Answer toSaveAnswer = Answer.builder()
                                        .user(userRepository.findById(userNo).orElseThrow(()->new NoSuchElementException("해당 사용자를 찾을 수 없습니다. USER-NO:"+userNo)))
                                        .question(questionRepository.findById(requestDto.getQuestionNo()).orElseThrow(()->new NoSuchElementException("해당 질문을 찾을 수 없습니다. QUESTION-NO:"+requestDto.getQuestionNo())))
                                        .content(requestDto.getContent())
                                        .build();
        Answer savedAnswer = answerRepository.save(toSaveAnswer);
        log.info("[SAVE ANSWER] no: {}", savedAnswer.getNo());
        return new AnswerResponseDto(savedAnswer);
    }

    public AnswerSliceResponseDto findAnswers(PageRequest pageRequest, Long questionNo) {
        Slice<Answer> slice = answerRepository.findByQuestionNoOrderByCreatedDateDesc(questionNo, pageRequest);
        List<AnswerResponseDto> content = slice.getContent().stream()
                                                            .map(AnswerResponseDto::new).collect(Collectors.toList());
        log.info("[READ ANSWERS] questionNo: {}, pageNum: {} ", questionNo, pageRequest.getPageNumber());
        return new AnswerSliceResponseDto(content,slice.hasNext());
    }

    public List<AnswerResponseDto> findTop3ByUserNo(Long no) {
        List<Answer> answerTop3List = answerRepository.findTop3ByUserNoOrderByCreatedDateDesc(no);
        log.info("[READ TOP3 ANSWERS] userNo: {}", no);
        return answerTop3List.stream()
                              .map(AnswerResponseDto::new)
                              .collect(Collectors.toList());
    }

    @Transactional
    public AnswerResponseDto update(Long no, AnswerRequestDto requestDto) {
        Answer answer = answerRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 답변을 찾을 수 없습니다. ANSWER-NO"+no));
        answer.updateAnswer(requestDto.getContent());
        log.info("[UDPATE ANSWER] answerNo: {}", no);
//        answerImgRepository.deleteByAnswerNo(no);
//        requestDto.getAnswerImgUrls()
//                .stream()
//                .map(imgUrl -> answerImgRepository.save(AnswerImg.builder()
//                        .answer(answer)
//                        .answerImgUrl(imgUrl)
//                        .build()));     !!!나중에 주석 제거
        return new AnswerResponseDto(answer);
    }

    public void delete(Long no) {
        answerRepository.delete(answerRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 답변을 찾을 수 없습니다. ANSWER-NO:"+no)));
        log.info("[DELETE ANSWER] no: {}", no);
    }

    public Long getAnswerWriter(Long no){
        Answer answer = answerRepository.findById(no).orElseThrow(()->new IllegalArgumentException("해당 답변을 찾을 수 없습니다. ANSWER-NO:"+no));
        log.info("[READ ANSWERWRITER] userNo: {}", answer.getUser().getNo());
        return answer.getUser().getNo();
    }
}
