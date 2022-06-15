package ddobab.blank.domain.question;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class QuestionRepositoryTest {

    @Autowired
    QuestionRepository questionRepository;

    @AfterEach
    public void cleanup() {
        questionRepository.deleteAll();
    }

    @Test
    public void findById() {

        //given
        String content = "테스트 입니다.";
        String writer = "test@gmail.com";

        questionRepository.save(Question.builder()
                .content(content)
//                .writer(writer)
                .build());

        //when
        List<Question> questionList = questionRepository.findAll();
//        Question question = questionRepository.findById()

        //then
        Question question = questionList.get(0);
        assertThat(question.getContent()).isEqualTo(content);
//        assertThat(question.getWriter()).isEqualTo(writer);
    }
}