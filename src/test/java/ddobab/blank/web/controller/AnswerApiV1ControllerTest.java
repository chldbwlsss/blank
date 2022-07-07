package ddobab.blank.web.controller;

import com.google.gson.Gson;
import ddobab.blank.security.annotation.LoginUser;
import ddobab.blank.security.annotation.LoginUserArgumentResolver;
import ddobab.blank.security.dto.SessionUserDto;
import ddobab.blank.service.answer.AnswerService;
import ddobab.blank.web.config.WebConfig;
import ddobab.blank.web.dto.AnswerRequestDto;
import ddobab.blank.web.dto.AnswerResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnswerApiV1Controller.class)
class AnswerApiV1ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnswerService answerService;

    @MockBean
    LoginUserArgumentResolver loginUserArgumentResolver;

    @WithMockUser
    @Test
    void 답변_생성() throws Exception {
        //given
        AnswerRequestDto answerRequestDto = AnswerRequestDto.builder()
                .content("테스트내용")
                .questionNo(1L)
                .build();
        Gson gson = new Gson();
        String requestBody = gson.toJson(answerRequestDto);
        AnswerResponseDto answerResponseDto = new AnswerResponseDto(1L, "테스트답변", "테스트작성자", 4L);
        given(answerService.save(any(),any(AnswerRequestDto.class))).willReturn(answerResponseDto);

        //when, then
        mockMvc.perform(post("/api/v1/answer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.no").value(answerResponseDto.getNo()))
                .andExpect(jsonPath("$.data.content").value(answerResponseDto.getContent()))
                .andExpect(jsonPath("$.data.writer").value(answerResponseDto.getWriter()))
                .andExpect(jsonPath("$.data.writerNo").value(answerResponseDto.getWriterNo()))
                .andDo(print());
        verify(answerService).save(any(),any(AnswerRequestDto.class));
    }
}