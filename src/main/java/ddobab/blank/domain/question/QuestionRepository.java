package ddobab.blank.domain.question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByUserNo(Long no);

    List<Question> findTop3ByUserNoOrderByCreatedDateDesc(Long no);

    void deleteByUserNo(Long no);

    List<Question> findByContentContainingIgnoreCaseOrderByCreatedDateDesc(String word);

    List<Question> findByCategoryAndContentContainingIgnoreCaseOrderByCreatedDateDesc(QuestionCategory category, String word);


}
