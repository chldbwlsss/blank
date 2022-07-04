package ddobab.blank.domain.question;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findTop3ByUserNoOrderByCreatedDateDesc(Long no);

    void deleteByUserNo(Long no);

    Slice<Question> findByContentContainingIgnoreCaseOrderByCreatedDateDesc(String word, Pageable pageable);

    Slice<Question> findByCategoryAndContentContainingIgnoreCaseOrderByCreatedDateDesc(QuestionCategory category, String word, Pageable pageable);

    List<Question> findTop5ByCreatedDateGreaterThanOrderByViewsDesc(LocalDateTime twoDaysAgo);
}
