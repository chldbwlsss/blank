package ddobab.blank.domain.answer;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Slice<Answer> findByQuestionNoOrderByCreatedDateDesc(Long no, Pageable pageable);

    List<Answer> findByUserNo(Long no);

    List<Answer> findTop3ByUserNoOrderByCreatedDateDesc(Long no);

    void deleteByQuestionNo(Long no);

    void deleteByUserNo(Long no);
}
