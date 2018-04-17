package es.nitaur.service;

import es.nitaur.domain.Quiz;
import es.nitaur.domain.QuizAnswer;
import es.nitaur.domain.QuizQuestion;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface QuizService {

    Collection<Quiz> findAll();

    Quiz findOne(Long id);

    Quiz create(Quiz quiz);

    void delete(Long id);

    QuizQuestion updateQuestion(Long id, QuizQuestion quiz);

    QuizQuestion answerQuestion(Long id, List<QuizAnswer> quizAnswers);

    QuizQuestion getQuestion(Long id);

    List<QuizQuestion> updateQuestions(List<QuizQuestion> quizQuestions);

    List<QuizQuestion> getQuestions(Long sectionId);
}
