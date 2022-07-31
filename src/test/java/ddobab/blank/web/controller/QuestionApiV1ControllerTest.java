package ddobab.blank.web.controller;

import com.google.gson.Gson;
import ddobab.blank.domain.question.Question;
import ddobab.blank.domain.question.QuestionCategory;
import ddobab.blank.domain.user.Role;
import ddobab.blank.domain.user.User;
import ddobab.blank.security.annotation.LoginUserArgumentResolver;
import ddobab.blank.service.question.QuestionService;
import ddobab.blank.web.dto.QuestionRequestDto;
import ddobab.blank.web.dto.QuestionResponseDto;
import org.junit.jupiter.api.DisplayName;
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
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuestionApiV1Controller.class)
class QuestionApiV1ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @MockBean
    LoginUserArgumentResolver loginUserArgumentResolver;

    @WithMockUser
    @Test
    @DisplayName("질문 저장 성공")
    void saveSuccess() throws Exception {

        QuestionRequestDto questionRequestDto = QuestionRequestDto.builder()
                                                            .content("저장요청질문")
                                                            .categoryValue("ART")
                                                            .build();
        Gson gson = new Gson();
        String requestBody = gson.toJson(questionRequestDto);
        QuestionResponseDto questionResponseDto = new QuestionResponseDto(1L, "ART", "저장요청질문", "tester", 1L, 1) ;

        given(questionService.save(any(), any(QuestionRequestDto.class))).willReturn(questionResponseDto);

        mockMvc.perform(post("/api/v1/question").contentType(MediaType.APPLICATION_JSON).content(requestBody).with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.categoryValue").value(questionResponseDto.getCategoryValue()))
                .andExpect(jsonPath("$.data.content").value(questionResponseDto.getContent()))
                .andExpect(jsonPath("$.data.writer").value(questionResponseDto.getWriter()))
                .andExpect(jsonPath("$.error").isEmpty())
                .andDo(print());

        verify(questionService).save(any(), any(QuestionRequestDto.class));
    }

    @WithMockUser
    @Test
    @DisplayName("질문 저장 실패 : 질문 내용은 필수")
    void saveFailure1() throws Exception {

        QuestionRequestDto questionRequestDto = QuestionRequestDto.builder()
                .content("")
                .categoryValue("ART")
                .build();
        Gson gson = new Gson();
        String requestBody = gson.toJson(questionRequestDto);

        mockMvc.perform(post("/api/v1/question")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error.errorCode").value("CLIENT"))
                .andExpect(jsonPath("$.error.message").value("질문 내용은 빈 칸일 수 없습니다."))
                .andDo(print());
        then(questionService).shouldHaveNoInteractions();
    }

    @WithMockUser
    @Test
    @DisplayName("질문 저장 실패 : 질문 내용은 500자 이하")
    void saveFailure2() throws Exception {

        QuestionRequestDto questionRequestDto = QuestionRequestDto.builder()
//                500자 이상 입력 시
                .content("1Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit")
                .categoryValue("ART")
                .build();
        Gson gson = new Gson();
        String requestBody = gson.toJson(questionRequestDto);

        mockMvc.perform(post("/api/v1/question")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error.errorCode").value("CLIENT"))
                .andExpect(jsonPath("$.error.message").value("질문은 500자 이하로 작성 가능합니다."))
                .andDo(print());
        then(questionService).shouldHaveNoInteractions();
    }

    @WithMockUser
    @Test
    @DisplayName("질문 저장 실패 : 카테고리는 필수")
    void saveFailure3() throws Exception {

        QuestionRequestDto questionRequestDto = QuestionRequestDto.builder()
                .content("질문내용")
                .categoryValue("")
                .build();
        Gson gson = new Gson();
        String requestBody = gson.toJson(questionRequestDto);

        mockMvc.perform(post("/api/v1/question")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error.errorCode").value("CLIENT"))
                .andExpect(jsonPath("$.error.message").value("카테고리를 선택해야 합니다."))
                .andDo(print());
        then(questionService).shouldHaveNoInteractions();
    }

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
    @DisplayName("질문 수정 성공")
    void updateSuccess() throws Exception {

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
    @DisplayName("질문 수정 실패 : 질문 내용은 필수")
    void updateFailure1() throws Exception {

        QuestionRequestDto questionRequestDto = QuestionRequestDto.builder()
                .content("")
                .categoryValue("ART")
                .build();
        Gson gson = new Gson();
        String requestBody = gson.toJson(questionRequestDto);

        mockMvc.perform(put("/api/v1/question/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error.errorCode").value("CLIENT"))
                .andExpect(jsonPath("$.error.message").value("질문 내용은 빈 칸일 수 없습니다."))
                .andDo(print());
        then(questionService).shouldHaveNoInteractions();
    }

    @WithMockUser
    @Test
    @DisplayName("질문 수정 실패 : 질문 내용은 500자 이하")
    void updateFailure2() throws Exception {

        QuestionRequestDto questionRequestDto = QuestionRequestDto.builder()
//                500자 이상 입력 시
                .content("1Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit")
                .categoryValue("ART")
                .build();
        Gson gson = new Gson();
        String requestBody = gson.toJson(questionRequestDto);

        mockMvc.perform(put("/api/v1/question/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error.errorCode").value("CLIENT"))
                .andExpect(jsonPath("$.error.message").value("질문은 500자 이하로 작성 가능합니다."))
                .andDo(print());
        then(questionService).shouldHaveNoInteractions();
    }

    @WithMockUser
    @Test
    @DisplayName("질문 수정 실패 : 카테고리는 필수")
    void updateFailure3() throws Exception {

        QuestionRequestDto questionRequestDto = QuestionRequestDto.builder()
                .content("질문내용")
                .categoryValue("")
                .build();
        Gson gson = new Gson();
        String requestBody = gson.toJson(questionRequestDto);

        mockMvc.perform(put("/api/v1/question/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.error.errorCode").value("CLIENT"))
                .andExpect(jsonPath("$.error.message").value("카테고리를 선택해야 합니다."))
                .andDo(print());
        then(questionService).shouldHaveNoInteractions();
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