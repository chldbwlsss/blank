package ddobab.blank.service.answer;

import ddobab.blank.domain.answer.Answer;
import ddobab.blank.domain.answer.AnswerImg;
import ddobab.blank.domain.answer.AnswerImgRepository;
import ddobab.blank.domain.answer.AnswerRepository;
import ddobab.blank.domain.question.QuestionRepository;
import ddobab.blank.domain.user.UserRepository;
import ddobab.blank.web.dto.AnswerResponseDto;
import ddobab.blank.web.dto.AnswerSaveRequestDto;
import ddobab.blank.web.dto.AnswerUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerImgRepository answerImgRepository;

    @Transactional
    public AnswerResponseDto save(AnswerSaveRequestDto requestDto) {
        Answer toSaveAnswer = Answer.builder()
                                        .user(userRepository.findById(requestDto.getUserNo()).get())
                                        .question(questionRepository.findById(requestDto.getQuestionNo()).get())
                                        .content(requestDto.getContent())
                                        .build();
        Answer savedAnswer = answerRepository.save(toSaveAnswer);
        //답변사진 저장해야됨!!!
        return new AnswerResponseDto(answerRepository.findById(savedAnswer.getNo()).get());
    }

    public List<AnswerResponseDto> findByQuestionNo(Long no) {
        List<Answer> answerList = answerRepository.findByQuestionNo(no);
        List<AnswerResponseDto> responseDtoList = answerList.stream()
                                                    .map(answer -> new AnswerResponseDto(answer))
                                          .collect(Collectors.toList());

        return responseDtoList;
    }

    @Transactional
    public AnswerResponseDto update(Long no, AnswerUpdateRequestDto requestDto) {
        Answer answer = answerRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 답변을 찾을 수 없습니다."));
        answer.updateAnswer(requestDto.getContent());

//        answerImgRepository.deleteByAnswerNo(no);
//        requestDto.getAnswerImgUrls()
//                .stream()
//                .map(imgUrl -> answerImgRepository.save(AnswerImg.builder()
//                        .answer(answer)
//                        .answerImgUrl(imgUrl)
//                        .build()));     !!!나중에 주석 제거
        return new AnswerResponseDto(answerRepository.findById(no).get());
    }

    public void delete(Long no) {
        Answer answer = answerRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 답변입니다."));
        answerRepository.deleteById(no);
    }
}