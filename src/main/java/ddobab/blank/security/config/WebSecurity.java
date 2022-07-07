package ddobab.blank.security.config;

import ddobab.blank.security.dto.SessionUserDto;
import ddobab.blank.service.answer.AnswerService;
import ddobab.blank.service.question.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component("webSecurity")
public class WebSecurity {
    private final QuestionService questionService;
    private final AnswerService answerService;

    public boolean checkUserAuthority(Long userNo, SessionUserDto loginUser){
        log.info("USER-NO:{}, LOGIN-USER:{}",userNo, loginUser);
        return userNo.equals(loginUser.getNo());
    }

    public boolean checkQuestionAuthority(Long questionNo, SessionUserDto loginUser){
        Long writerNo = questionService.getQuestionWriter(questionNo);
        log.info("WRITER-NO:{}, LOGIN-USER:{}",writerNo, loginUser);
        return writerNo.equals(loginUser.getNo());
    }

    public boolean checkAnswerAuthority(Long answerNo, SessionUserDto loginUser){
        Long writerNo = answerService.getAnswerWriter(answerNo);
        log.info("WRITER-NO:{}, LOGIN-USER:{}",writerNo, loginUser);
        return writerNo.equals(loginUser.getNo());
    }
}
