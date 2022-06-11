package ddobab.blank.web;

import ddobab.blank.domain.question.Question;
import ddobab.blank.domain.question.QuestionCategory;
import ddobab.blank.domain.question.QuestionRepository;
import ddobab.blank.service.question.QuestionService;
import ddobab.blank.web.dto.QuestionResponseDto;
import ddobab.blank.web.dto.QuestionSaveRequestDto;
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
class QuestionApiV1ControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionService questionService;

//    @AfterEach
//    public void tearDown() throws Exception {
//        questionRepository.deleteAll();
//    }

    @Test
    public void Question_등록() throws Exception {

        //given
        String content = "content";
        String writer = "testUser";
        String categoryEngValue = "ART";

        QuestionSaveRequestDto requestDto = QuestionSaveRequestDto.builder()
                .content(content)
                .writer(writer)
                .categoryValue(categoryEngValue)
                .build();

        String url = "http://localhost:" + port + "/api/v1/question";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Question> allQuestions = questionRepository.findAll();
        assertThat(allQuestions.get(0).getContent()).isEqualTo(content);
        assertThat(allQuestions.get(0).getWriter()).isEqualTo(writer);
        assertThat(allQuestions.get(0).getCategory()).isEqualTo(QuestionCategory.ART);
        assertThat(allQuestions.get(0).getViews()).isEqualTo(0);

    }

    @Test
    public void Question_조회() {

        //given
        String content = "content";
        String writer = "testUser";

        QuestionSaveRequestDto requestDto = QuestionSaveRequestDto.builder()
                .content(content)
                .writer(writer)
                .build();

        Long savedNo = questionRepository.save(requestDto.toEntity()).getNo();

        String url = "http://localhost:" + port + "/api/v1/question/" + savedNo;

        //when
        ResponseEntity<QuestionResponseDto> responseEntity = restTemplate.getForEntity(url, QuestionResponseDto.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getNo()).isEqualTo(savedNo);
        assertThat(responseEntity.getBody().getContent()).isEqualTo(content);
        assertThat(responseEntity.getBody().getWriter()).isEqualTo(writer);
        assertThat(responseEntity.getBody().getViews()).isEqualTo(1);

    }

}