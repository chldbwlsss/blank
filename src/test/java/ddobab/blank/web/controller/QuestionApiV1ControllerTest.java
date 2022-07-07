package ddobab.blank.web.controller;

import com.google.gson.Gson;
import ddobab.blank.domain.question.Question;
import ddobab.blank.domain.question.QuestionCategory;
import ddobab.blank.domain.user.Role;
import ddobab.blank.domain.user.User;
import ddobab.blank.service.question.QuestionService;
import ddobab.blank.web.dto.QuestionRequestDto;
import ddobab.blank.web.dto.QuestionResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;

@WebMvcTest(QuestionApiV1Controller.class)
class QuestionApiV1ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

//    @WithMockUser
//    @Test
//    void 질문_저장() throws Exception {
//
//        QuestionRequestDto questionRequestDto = QuestionRequestDto.builder()
//                                                            .content("저장요청질문")
//                                                            .categoryValue("ART")
//                                                            .build();
//        Gson gson = new Gson();
//        String requestBody = gson.toJson(questionRequestDto);
//        QuestionResponseDto questionResponseDto = new QuestionResponseDto(1L, "ART", "저장요청질문", "tester", 1L, 1) ;
//
//        given(questionService.save(eq(1L), any(QuestionRequestDto.class)))
//                .willReturn(questionResponseDto);
//
//        mockMvc.perform(post("/api/v1/question").contentType(MediaType.APPLICATION_JSON).content(requestBody).with(csrf()))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data.categoryValue").value(questionResponseDto.getCategoryValue()))
//                .andExpect(jsonPath("$.data.content").value(questionResponseDto.getContent()))
//                .andExpect(jsonPath("$.data.writer").value(questionResponseDto.getWriter()))
//                .andExpect(jsonPath("$.error").isEmpty())
//                .andDo(print());
//
//        verify(questionService).save(eq(1L), any(QuestionRequestDto.class));
//    }

    @WithMockUser
    @Test
    void 질문_조회() throws Exception {

        Question questionEntity = Question.builder()
                .content("test 내용")
                .category(QuestionCategory.ART)
                .user(User.builder()
                        .nickname("닉네임")
                        .role(Role.USER)
                        .email("test@gmail.com")
                        .profileImgUrl("testProfileImgUrl")
                            .build())
                .views(1)
                .build();

        QuestionResponseDto questionResponseDto = new QuestionResponseDto(questionEntity);

        given(questionService.findByNo(1L)).willReturn(questionResponseDto);

        mockMvc.perform(
                        get("/api/v1/question/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").value(questionResponseDto.getContent()))
                .andExpect(jsonPath("$.data.categoryValue").value(questionResponseDto.getCategoryValue()))
                .andExpect(jsonPath("$.data.writer").value(questionResponseDto.getWriter()))
                .andExpect(jsonPath("$.data.views").value(1))
                .andExpect(jsonPath("$.error").isEmpty())
                .andDo(print());
        verify(questionService).findByNo(1L);
    }

    @WithMockUser
    @Test
    void 질문_수정() throws Exception {

        QuestionRequestDto questionRequestDto = QuestionRequestDto.builder()
                                                                .content("변경요청질문")
                                                                .categoryValue("ART")
                                                                .build();
        Gson gson = new Gson();
        String requestBody = gson.toJson(questionRequestDto);
        QuestionResponseDto questionResponseDto = new QuestionResponseDto(1L, "ART", "변경요청질문", "tester", 1L, 1) ;

        given(questionService.update(eq(1L), any(QuestionRequestDto.class)))
                .willReturn(questionResponseDto);

        mockMvc.perform(put("/api/v1/question/1").contentType(MediaType.APPLICATION_JSON).content(requestBody).with(csrf()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.data.categoryValue").value(questionResponseDto.getCategoryValue()))
                .andExpect(jsonPath("$.data.content").value(questionResponseDto.getContent()))
                .andExpect(jsonPath("$.data.writer").value(questionResponseDto.getWriter()))
                .andExpect(jsonPath("$.error").isEmpty())
                .andDo(print());

        verify(questionService).update(eq(1L), any(QuestionRequestDto.class));
    }

    @WithMockUser
    @Test
    void 질문_삭제() throws Exception {

        Question questionEntity = Question.builder()
                .content("test 내용")
                .category(QuestionCategory.ART)
                .user(User.builder()
                        .nickname("닉네임")
                        .role(Role.USER)
                        .email("test@gmail.com")
                        .profileImgUrl("testProfileImgUrl")
                        .build())
                .views(1)
                .build();

        willDoNothing().given(questionService).delete(1L);

        mockMvc.perform(delete("/api/v1/question/1").contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error").isEmpty())
                .andDo(print());

        verify(questionService).delete(1L);
    }

    @WithMockUser
    @Test
    void 카테고리_리스트() throws Exception {

        mockMvc.perform(get("/api/v1/question/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.error").isEmpty())
                .andDo(print());
    }

    @WithMockUser
    @Test
    void 질문_핫이슈_top5() throws Exception{
        Question questionEntity = Question.builder()
                .content("test 내용")
                .category(QuestionCategory.ART)
                .user(User.builder()
                        .nickname("닉네임")
                        .role(Role.USER)
                        .email("test@gmail.com")
                        .profileImgUrl("testProfileImgUrl")
                        .build())
                .views(1)
                .build();

        QuestionResponseDto questionResponseDto = new QuestionResponseDto(questionEntity);
        List<QuestionResponseDto> questionList = new ArrayList<QuestionResponseDto>();

        questionList.add(questionResponseDto);

        given(questionService.getTop5ByViews()).willReturn(questionList);

        mockMvc.perform(
                        get("/api/v1/question/top5").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].no").value(questionResponseDto.getNo()))
                .andExpect(jsonPath("$.data[0].categoryValue").value(questionResponseDto.getCategoryValue()))
                .andExpect(jsonPath("$.data[0].content").value(questionResponseDto.getContent()))
                .andExpect(jsonPath("$.data[0].writer").value(questionResponseDto.getWriter()))
                .andExpect(jsonPath("$.data[0].views").value(questionResponseDto.getViews()))
                .andExpect(jsonPath("$.error").isEmpty())
                .andDo(print());

        verify(questionService).getTop5ByViews();

    }
}