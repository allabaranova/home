package es.nitaur.repository;

import es.nitaur.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    QuizQuestion findOne(Long id);
}
