package ddobab.blank.web.controller;

import ddobab.blank.domain.question.Question;
import ddobab.blank.domain.question.QuestionCategory;
import ddobab.blank.domain.user.Role;
import ddobab.blank.domain.user.User;
import ddobab.blank.service.question.QuestionService;
import ddobab.blank.web.dto.QuestionResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuestionApiV1Controller.class)
class QuestionApiV1ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @WithMockUser
    @Test
    @DisplayName("질문 가져오기")
    void getQuestion() throws Exception {

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
                        get("/api/v1/question" + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content", is("test 내용")))
                .andExpect(jsonPath("$.data.categoryValue", is("예술")))
                .andExpect(jsonPath("$.data.writer", is("닉네임")))
                .andExpect(jsonPath("$.data.views", is(1)))
                .andDo(print());
    }
}