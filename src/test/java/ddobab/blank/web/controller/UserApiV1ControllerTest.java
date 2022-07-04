package ddobab.blank.web.controller;

import ddobab.blank.service.answer.AnswerService;
import ddobab.blank.service.question.QuestionService;
import ddobab.blank.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserApiV1Controller.class)
class UserApiV1ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    private QuestionService questionService;
    private AnswerService answerService;


}