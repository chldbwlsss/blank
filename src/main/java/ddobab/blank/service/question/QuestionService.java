package ddobab.blank.service.question;

import ddobab.blank.domain.question.Question;
import ddobab.blank.domain.question.QuestionRepository;
import ddobab.blank.web.dto.QuestionResponseDto;
import ddobab.blank.web.dto.QuestionSaveRequestDto;
import ddobab.blank.web.dto.QuestionUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Transactional
    public Long save(QuestionSaveRequestDto requestDto) {
        return questionRepository.save(requestDto.toEntity()).getNo();  //저장된 question 'entity' 반환
    }

    @Transactional
    public Long update(Long no, QuestionUpdateRequestDto requestDto) {
        Question question = questionRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문을 찾을 수 없습니다. no= " + no));

        question.update(requestDto.getContent());

        return no;
    }

    public QuestionResponseDto findByNo(Long no) {
        Question entity = questionRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문을 찾을 수 없습니다. no= " + no));

        return new QuestionResponseDto(entity);
    }
}
