package ddobab.blank.service.user;

import ddobab.blank.domain.answer.AnswerRepository;
import ddobab.blank.domain.question.QuestionRepository;
import ddobab.blank.domain.user.User;
import ddobab.blank.domain.user.UserRepository;
import ddobab.blank.web.dto.UserResponseDto;
import ddobab.blank.web.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public UserResponseDto findByNo(Long no) {
        User user = userRepository.findById(no)
                .orElseThrow(() -> new NoSuchElementException("해당 사용자를 찾을 수 없습니다. USER-NO:"+no));
        log.info("[READ USER] userNo: {}", no);
        return new UserResponseDto(user);
    }

    @Transactional
    public UserResponseDto update(Long no, UserRequestDto requestDto) {
            User user = userRepository.findById(no)
                    .orElseThrow(() -> new NoSuchElementException("해당 사용자를 찾을 수 없습니다. USER-NO:"+no));
            user.updateUser(requestDto.getNickname());
            log.info("[UPDATE USER] userNo: {}", no);
            return new UserResponseDto(user);
    }

    @Transactional
    public void delete(Long no) {
        answerRepository.deleteByUserNo(no); //cascade관련 수정필요
        questionRepository.deleteByUserNo(no);
        userRepository.delete(userRepository.findById(no)
                .orElseThrow(() -> new NoSuchElementException("해당 사용자를 찾을 수 없습니다. USER-NO:"+no)));
        log.info("[DELETE USER] userNo: {}", no);
    }
}
