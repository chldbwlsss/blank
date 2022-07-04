package ddobab.blank.web.controller;

import ddobab.blank.domain.question.Question;
import ddobab.blank.domain.question.QuestionCategory;
import ddobab.blank.domain.question.QuestionRepository;
import ddobab.blank.domain.user.User;
import ddobab.blank.service.question.QuestionService;
import ddobab.blank.web.dto.QuestionResponseDto;
import ddobab.blank.web.dto.QuestionSaveRequestDto;
import ddobab.blank.web.dto.QuestionUpdateRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

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
        String content = "testcontent";
//        String writer = "testUser";
        String categoryEngValue = "ART";

        QuestionSaveRequestDto requestDto = QuestionSaveRequestDto.builder()
                .content(content)
//                .writer(writer)
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
//        assertThat(allQuestions.get(0).getWriter()).isEqualTo(writer);
        assertThat(allQuestions.get(0).getCategory()).isEqualTo(QuestionCategory.ART);
        assertThat(allQuestions.get(0).getViews()).isEqualTo(0);

    }

//    @Test
//    public void Question_조회() {
//        User user = User.builder()
//                .nickname("testNickname")
//                .email("testEmail")
//                .profileImgUrl("testProfileImg")
//                .build();
//        //given
//        String content = "content";
//        String writer = user.getNickname();
//        String categoryEngValue = "ART";
//
//        QuestionSaveRequestDto requestDto = QuestionSaveRequestDto.builder()
//                .content(content)
////                .writer(writer)
//                .categoryValue(categoryEngValue)
//                .build();
//
//        Long savedNo = questionRepository.save(requestDto.toEntity()).getNo();
//
//        String url = "http://localhost:" + port + "/api/v1/question/" + savedNo;
//
//        //when
//        ResponseEntity<QuestionResponseDto> responseEntity = restTemplate.getForEntity(url, QuestionResponseDto.class);
//
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody().getNo()).isEqualTo(savedNo);
//        assertThat(responseEntity.getBody().getContent()).isEqualTo(content);
//        assertThat(responseEntity.getBody().getWriter()).isEqualTo(writer);
//        assertThat(responseEntity.getBody().getCategoryValue()).isEqualTo(QuestionCategory.ART.toString());
//        assertThat(responseEntity.getBody().getViews()).isEqualTo(1);
//    }
//
//    @Test
//    public void Question_수정() {
//        User user = User.builder()
//                .nickname("testNickname")
//                .email("testEmail")
//                .profileImgUrl("testProfileImg")
//                .build();
//        //given
//        Question saveQuestion = questionRepository.save(Question.builder()
//                .content("testContent")
//                .user(user)
//                .category(QuestionCategory.ART)
//                .build());
//
//        Long toUpdateNo = saveQuestion.getNo();
//        String changedContent = "changeContent";
//        String changedCategory = "ECONOMY";
//
//        QuestionUpdateRequestDto updateRequestDto = QuestionUpdateRequestDto.builder()
//                .content(changedContent)
//                .categoryValue(changedCategory)
//                .build();
//
//        String url = "http://localhost:" + port + "/api/v1/question/" + toUpdateNo;
//
//        HttpEntity<QuestionUpdateRequestDto> requestEntity = new HttpEntity<>(updateRequestDto);
//
//        //when
//        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
//
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);
//        Question question = questionRepository.findById(toUpdateNo).get();
//        assertThat(question.getContent()).isEqualTo(changedContent);
//        assertThat(question.getCategory()).isEqualTo(QuestionCategory.ECONOMY);
//    }
//
//    @Test
//    public void Question_삭제() {
//        //given
//        String content = "content";
//        String writer = "testUser";
//        String categoryEngValue = "ART";
//
//        QuestionSaveRequestDto requestDto = QuestionSaveRequestDto.builder()
//                .content(content)
////                .writer(writer)
//                .categoryValue(categoryEngValue)
//                .build();
//
//        Long savedNo = questionRepository.save(requestDto.toEntity()).getNo();
//
//        String url = "http://localhost:" + port + "/api/v1/question/" + savedNo;
//
//        //when
//        ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
//        Optional<Question> question = questionRepository.findById(savedNo);
//
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isNull();
//        assertThat(question.isPresent()).isFalse();
//    }

}