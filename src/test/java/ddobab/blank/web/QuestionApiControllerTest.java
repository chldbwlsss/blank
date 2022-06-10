package ddobab.blank.web;

import ddobab.blank.domain.question.Question;
import ddobab.blank.domain.question.QuestionRepository;
import ddobab.blank.web.dto.QuestionSaveRequestDto;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuestionApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private QuestionRepository questionRepository;

    @AfterEach
    public void tearDown() throws Exception {
        questionRepository.deleteAll();
    }

    @Test
    public void Question_등록() throws Exception {

        //given
        String content = "content";
        QuestionSaveRequestDto requestDto = QuestionSaveRequestDto.builder()
                .content(content)
                .writer("testUser")
                .build();

        String url = "http://localhost:" + port + "/api/question";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Question> allQuestions = questionRepository.findAll();
        assertThat(allQuestions.get(0).getContent()).isEqualTo(content);
        assertThat(allQuestions.get(0).getWriter()).isEqualTo("testUser");

    }

}