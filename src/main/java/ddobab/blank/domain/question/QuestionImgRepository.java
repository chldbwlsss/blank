package ddobab.blank.domain.question;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionImgRepository extends JpaRepository<QuestionImg, Long> {

    void deleteByQuestionNo(Long questionNo);
}
