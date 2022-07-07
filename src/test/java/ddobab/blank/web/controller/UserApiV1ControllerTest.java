package ddobab.blank.web.controller;

import com.google.gson.Gson;
import ddobab.blank.domain.user.Role;
import ddobab.blank.domain.user.User;
import ddobab.blank.service.answer.AnswerService;
import ddobab.blank.service.question.QuestionService;
import ddobab.blank.service.user.UserService;
import ddobab.blank.web.dto.AnswerResponseDto;
import ddobab.blank.web.dto.QuestionResponseDto;
import ddobab.blank.web.dto.UserResponseDto;
import ddobab.blank.web.dto.UserRequestDto;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @WithMockUser
    @Test
    void 유저_프로필_조회() throws Exception {
        //given
        User userEntity = User.builder()
                .nickname("테스트유저")
                .email("test-email@gmail.com")
                .profileImgUrl("test_img_url")
                .role(Role.USER)
                .build();
        UserResponseDto userResponseDto = new UserResponseDto(userEntity);

        given(userService.findByNo(1L)).willReturn(userResponseDto);

        //when, then
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

        //given
        UserRequestDto userRequestDto = UserRequestDto.builder()
                                                    .nickname("변경된테스트유저")
                                                    .build();
        Gson gson = new Gson();
        String requestBody = gson.toJson(userRequestDto);
        UserResponseDto userResponseDto = new UserResponseDto(1L, "변경된테스트유저", "test-email@gmail.com", "test_img_url");

        given(userService.update(eq(1L), any(UserRequestDto.class)))
                .willReturn(userResponseDto);

        //when, then
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
        verify(userService).update(eq(1L), any(UserRequestDto.class));
    }

    @WithMockUser
    @Test
    void 유저_프로필_삭제() throws Exception {
        //given
        willDoNothing().given(userService).delete(1L);

        //when, then
        mockMvc.perform(delete("/api/v1/user/1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error").isEmpty()
                ).andDo(print());
        verify(userService).delete(1L);
    }

    @WithMockUser
    @Test
    void 유저_최근질문3개_조회() throws Exception {
        //given
        QuestionResponseDto questionResponseDto = new QuestionResponseDto(1L, "ART", "테스트질문내용", "테스트작성자", 4L, 3);
        List<QuestionResponseDto> list = new ArrayList<>();
        list.add(questionResponseDto);

        given(questionService.findTop3ByUserNo(4L)).willReturn(list);

        //when, then
        mockMvc.perform(get("/api/v1/user/4/question/top3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].no").value(questionResponseDto.getNo()))
                .andExpect(jsonPath("$.data[0].categoryValue").value(questionResponseDto.getCategoryValue()))
                .andExpect(jsonPath("$.data[0].content").value(questionResponseDto.getContent()))
                .andExpect(jsonPath("$.data[0].writer").value(questionResponseDto.getWriter()))
                .andExpect(jsonPath("$.data[0].writerNo").value(questionResponseDto.getWriterNo()))
                .andExpect(jsonPath("$.data[0].views").value(questionResponseDto.getViews()))
                .andDo(print());
        verify(questionService).findTop3ByUserNo(4L);
    }

    @WithMockUser
    @Test
    void 유저_최근답변3개_조회() throws Exception {
        //given
        AnswerResponseDto answerResponseDto = new AnswerResponseDto(1L, "테스트답변내용", "테스트작성자", 4L);
        List<AnswerResponseDto> list = new ArrayList<>();
        list.add(answerResponseDto);

        given(answerService.findTop3ByUserNo(4L)).willReturn(list);

        //when, then
        mockMvc.perform(get("/api/v1/user/4/answer/top3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].no").value(answerResponseDto.getNo()))
                .andExpect(jsonPath("$.data[0].content").value(answerResponseDto.getContent()))
                .andExpect(jsonPath("$.data[0].writer").value(answerResponseDto.getWriter()))
                .andExpect(jsonPath("$.data[0].writerNo").value(answerResponseDto.getWriterNo()))
                .andDo(print());
        verify(answerService).findTop3ByUserNo(4L);
    }
}