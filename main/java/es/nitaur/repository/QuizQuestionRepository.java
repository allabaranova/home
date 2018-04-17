package es.nitaur.repository;

import es.nitaur.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

    @Query("SELECT qq from QuizQuestion qq where qq.section.id = :id")
    List<QuizQuestion> findAllBySectionId(@Param("id") Long filterId);
}
