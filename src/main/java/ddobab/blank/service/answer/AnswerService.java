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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
        //답변사진 저장해야됨!!!
        return new AnswerResponseDto(answerRepository.findById(savedAnswer.getNo()).orElseThrow(()->new IllegalStateException("답변 저장이 완료되지 않았습니다.")));
    }

    public AnswerSliceResponseDto findAnswers(PageRequest pageRequest, Long no) {
        Slice<Answer> slice = answerRepository.findByQuestionNoOrderByCreatedDateDesc(no, pageRequest);
        List<AnswerResponseDto> content = slice.getContent().stream()
                                                            .map(AnswerResponseDto::new).collect(Collectors.toList());
        return new AnswerSliceResponseDto(content,slice.hasNext());
    }

    public List<AnswerResponseDto> findTop3ByUserNo(Long no) {
        List<Answer> answerTop3List = answerRepository.findTop3ByUserNoOrderByCreatedDateDesc(no);
        return answerTop3List.stream()
                              .map(AnswerResponseDto::new)
                              .collect(Collectors.toList());
    }

    @Transactional
    public AnswerResponseDto update(Long no, AnswerRequestDto requestDto) {
        Answer answer = answerRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 답변을 찾을 수 없습니다. ANSWER-NO"+no));
        answer.updateAnswer(requestDto.getContent());

//        answerImgRepository.deleteByAnswerNo(no);
//        requestDto.getAnswerImgUrls()
//                .stream()
//                .map(imgUrl -> answerImgRepository.save(AnswerImg.builder()
//                        .answer(answer)
//                        .answerImgUrl(imgUrl)
//                        .build()));     !!!나중에 주석 제거
        return new AnswerResponseDto(answerRepository.findById(no).orElseThrow(()->new IllegalStateException("답변 변경이 완료되지 않았습니다.")));
    }

    public void delete(Long no) {
        answerRepository.delete(answerRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 답변을 찾을 수 없습니다. ANSWER-NO:"+no)));
    }

    public Long getAnswerWriter(Long no){
        Answer answer = answerRepository.findById(no).orElseThrow(()->new IllegalArgumentException("해당 답변을 찾을 수 없습니다. ANSWER-NO:"+no));
        return answer.getUser().getNo();
    }
}
