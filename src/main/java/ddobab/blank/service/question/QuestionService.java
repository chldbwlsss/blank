package ddobab.blank.service.question;

import ddobab.blank.domain.question.*;
import ddobab.blank.web.dto.QuestionResponseDto;
import ddobab.blank.web.dto.QuestionSaveRequestDto;
import ddobab.blank.web.dto.QuestionUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionImgRepository questionImgRepository;

    @Transactional
    public Long save(QuestionSaveRequestDto requestDto) {
        return questionRepository.save(requestDto.toEntity()).getNo();  //저장된 question 'entity' 반환
    }

    @Transactional
    public QuestionResponseDto findByNo(Long no) {
        Question question = questionRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문을 찾을 수 없습니다."));

        question.plusViews();

        return new QuestionResponseDto(question);
    }

    @Transactional
    public Long update(Long no, QuestionUpdateRequestDto requestDto) {
        Question question = questionRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문을 찾을 수 없습니다."));
        question.updateQuestion(requestDto.getContent(), QuestionCategory.valueOf(requestDto.getCategoryValue()));

        questionImgRepository.deleteByQuestionNo(no);
        requestDto.getQuestionImgUrls()
                .stream()
                .map(imgUrl -> questionImgRepository.save(QuestionImg.builder()
                        .question(question)
                        .questionImgUrl(imgUrl)
                        .build()));
        return no;
    }

    public void delete(Long no) {
        Question question = questionRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문입니다."));
        questionRepository.deleteById(no);
    }
}
