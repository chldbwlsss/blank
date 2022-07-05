package ddobab.blank.web.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.google.gson.Gson;
import ddobab.blank.domain.user.Role;
import ddobab.blank.domain.user.User;
import ddobab.blank.service.answer.AnswerService;
import ddobab.blank.service.question.QuestionService;
import ddobab.blank.service.user.UserService;
import ddobab.blank.web.dto.UserResponseDto;
import ddobab.blank.web.dto.UserUpdateRequestDto;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;

@WebMvcTest(UserApiV1Controller.class)
class UserApiV1ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private AnswerService answerService;

    @Test
    void 로그인_유저_조회(){

    }

    @WithMockUser
    @Test
    void 유저_프로필_조회() throws Exception {

        User userEntity = User.builder()
                .nickname("테스트유저")
                .email("test-email@gmail.com")
                .profileImgUrl("test_img_url")
                .role(Role.USER)
                .build();
        UserResponseDto userResponseDto = new UserResponseDto(userEntity);

        given(userService.findByNo(1L)).willReturn(userResponseDto);

        mockMvc.perform(get("/api/v1/user/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.data.nickname").value(userResponseDto.getNickname())
                ).andExpect(
                        jsonPath("$.data.email").value(userResponseDto.getEmail())
                ).andExpect(
                        jsonPath("$.data.profileImgUrl").value(userResponseDto.getProfileImgUrl())
                ).andExpect(
                        jsonPath("$.error").isEmpty()
                ).andDo(print());
        verify(userService).findByNo(1L);
    }

    @WithMockUser
    @Test
    void 유저_프로필_수정() throws Exception {


        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                                                                        .nickname("변경된테스트유저")
                                                                        .build();
        Gson gson = new Gson();
        String requestBody = gson.toJson(userUpdateRequestDto);
        UserResponseDto userResponseDto = new UserResponseDto(1L, "변경된테스트유저", "test-email@gmail.com", "test_img_url");

        given(userService.update(eq(1L), any(UserUpdateRequestDto.class)))
                .willReturn(userResponseDto);

        mockMvc.perform(put("/api/v1/user/1").contentType(MediaType.APPLICATION_JSON).content(requestBody).with(csrf()))
                .andExpect(status().isAccepted())
                .andExpect(
                        jsonPath("$.data.nickname").value(userResponseDto.getNickname())
                ).andExpect(
                        jsonPath("$.data.email").value(userResponseDto.getEmail())
                ).andExpect(
                        jsonPath("$.data.profileImgUrl").value(userResponseDto.getProfileImgUrl())
                ).andExpect(
                        jsonPath("$.error").isEmpty()
                ).andDo(print());

        verify(userService).update(eq(1L), any(UserUpdateRequestDto.class));
    }


}