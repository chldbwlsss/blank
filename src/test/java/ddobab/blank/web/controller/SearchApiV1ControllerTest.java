package ddobab.blank.web.controller;


import ddobab.blank.service.search.SearchService;
import ddobab.blank.web.dto.QuestionResponseDto;
import ddobab.blank.web.dto.QuestionSliceResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SearchApiV1Controller.class)
class SearchApiV1ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchService searchService;

    @WithMockUser
    @Test
    void 검색() throws Exception {
        //given
        PageRequest pageRequest = PageRequest.of(Integer.parseInt("1"),Integer.parseInt("5"));
        QuestionResponseDto questionResponseDto = new QuestionResponseDto(1L, "ART", "테스트검색어", "테스트작성자", 4L, 3);
        List<QuestionResponseDto> list = new ArrayList<>();
        list.add(questionResponseDto);
        QuestionSliceResponseDto slice = new QuestionSliceResponseDto(list, true);
        given(searchService.findSearchedQuestions(pageRequest, "ART", "테스트검색어")).willReturn(slice);

        //when, then
        mockMvc.perform(get("/api/v1/search/question")
                        .param("categoryValue", "ART")
                        .param("word", "테스트검색어")
                        .param("page", "1")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.questions").isNotEmpty())
                .andExpect(jsonPath("$.data.hasNext").value(true))
                .andDo(print());
        verify(searchService).findSearchedQuestions(pageRequest, "ART", "테스트검색어");
    }
}