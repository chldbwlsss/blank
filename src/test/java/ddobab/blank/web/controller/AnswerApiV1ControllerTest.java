package ddobab.blank.web.controller;

import com.google.gson.Gson;
import ddobab.blank.security.annotation.LoginUserArgumentResolver;
import ddobab.blank.service.answer.AnswerService;
import ddobab.blank.web.dto.AnswerRequestDto;
import ddobab.blank.web.dto.AnswerResponseDto;
import ddobab.blank.web.dto.AnswerSliceResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
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

    @WithMockUser
    @Test
    void 질문번호로_답변리스트_페이징_조회() throws Exception {
        //given
        PageRequest pageRequest = PageRequest.of(Integer.parseInt("1"), Integer.parseInt("3"));
        AnswerResponseDto answerResponseDto = new AnswerResponseDto(1L, "테스트내용", "테스트작성자", 4L);
        List<AnswerResponseDto> list = new ArrayList<>();
        list.add(answerResponseDto);
        AnswerSliceResponseDto answerSliceResponseDto = new AnswerSliceResponseDto(list, true);

        given(answerService.findAnswers(pageRequest, 1L)).willReturn(answerSliceResponseDto);

        //when, then
        mockMvc.perform(get("/api/v1/answer")
                        .param("questionNo", "1")
                        .param("page", "1")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.answers").isArray())
                .andExpect(jsonPath("$.data.hasNext").value(true))
                .andDo(print());
        verify(answerService).findAnswers(pageRequest, 1L);
    }

    @WithMockUser
    @Test
    void 답변_수정() throws Exception {
        //given
        AnswerRequestDto answerRequestDto = AnswerRequestDto.builder()
                .questionNo(1L)
                .content("변경된테스트내용")
                .build();
        Gson gson = new Gson();
        String requestBody = gson.toJson(answerRequestDto);
        AnswerResponseDto answerResponseDto = new AnswerResponseDto(1L, "변경된테스트내용", "테스트작성자", 4L);
        given(answerService.update(eq(1L), any(AnswerRequestDto.class))).willReturn(answerResponseDto);

        //when, then
        mockMvc.perform(put("/api/v1/answer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .with(csrf()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.data.no").value(answerResponseDto.getNo()))
                .andExpect(jsonPath("$.data.content").value(answerResponseDto.getContent()))
                .andExpect(jsonPath("$.data.writer").value(answerResponseDto.getWriter()))
                .andExpect(jsonPath("$.data.writerNo").value(answerResponseDto.getWriterNo()))
                .andDo(print());
        verify(answerService).update(eq(1L), any(AnswerRequestDto.class));
    }

    @WithMockUser
    @Test
    void 답변_삭제() throws Exception {
        //given
        willDoNothing().given(answerService).delete(1L);

        //when, then
        mockMvc.perform(delete("/api/v1/answer/1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error").isEmpty())
                .andDo(print());
        verify(answerService).delete(1L);
    }
}