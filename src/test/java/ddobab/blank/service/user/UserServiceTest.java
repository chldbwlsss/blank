package ddobab.blank.service.user;

import ddobab.blank.domain.answer.AnswerRepository;
import ddobab.blank.domain.question.QuestionRepository;
import ddobab.blank.domain.user.Role;
import ddobab.blank.domain.user.User;
import ddobab.blank.domain.user.UserRepository;
import ddobab.blank.web.dto.UserRequestDto;
import ddobab.blank.web.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void 유저번호로_유저_조회(){
        //given
        User user = new User(4L, "테스트유저", "test-email", "test-img", Role.USER);
        given(userRepository.findById(4L)).willReturn(Optional.of(user));

        //when
        UserResponseDto userResponseDto = userService.findByNo(4L);

        //then
        assertThat(userResponseDto.getNo()).isEqualTo(user.getNo());
        assertThat(userResponseDto.getNickname()).isEqualTo(user.getNickname());
        assertThat(userResponseDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(userResponseDto.getProfileImgUrl()).isEqualTo(user.getProfileImgUrl());
    }
    
    @Test
    void 유저_수정(){
        //given
        UserRequestDto userRequestDto = new UserRequestDto("테스트유저닉네임 변경됨");
        User user = new User(4L, "테스트유저닉네임", "test-email", "test-img", Role.USER);
        given(userRepository.findById(4L)).willReturn(Optional.of(user));
        //when
        UserResponseDto userResponseDto = userService.update(4L, userRequestDto);

        //then
        assertThat(userResponseDto.getNo()).isEqualTo(4L);
        assertThat(userResponseDto.getNickname()).isEqualTo(userRequestDto.getNickname());
    }

    @Test
    void 유저_삭제(){
        //given
        User user = new User(4L, "테스트유저닉네임", "test-email", "test-img", Role.USER);
        willDoNothing().given(answerRepository).deleteByUserNo(4L);
        willDoNothing().given(questionRepository).deleteByUserNo(4L);
        given(userRepository.findById(4L)).willReturn(Optional.of(user));
        willDoNothing().given(userRepository).delete(user);

        //when, then
        userService.delete(4L);

    }
}