package es.nitaur.service.impl;

import com.google.common.collect.Lists;
import es.nitaur.domain.Quiz;
import es.nitaur.domain.QuizAnswer;
import es.nitaur.domain.QuizQuestion;
import es.nitaur.domain.QuizSection;
import es.nitaur.repository.QuizQuestionRepository;
import es.nitaur.repository.QuizRepository;
import es.nitaur.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    private static final Logger logger = LoggerFactory.getLogger(QuizServiceImpl.class);
    @Autowired
    private QuizQuestionRepository quizQuestionRepository;
    @Autowired
    private QuizRepository quizRepository;

    @Override
    public Collection<Quiz> findAll() {
        final Collection<Quiz> quizzes = quizRepository.findAll();
        return quizzes;
    }

    @Override
    public Quiz findOne(final Long id) {
        final Quiz quiz = quizRepository.findOne(id);
        return quiz;
    }

    public Quiz create(final Quiz quiz) {
        if (quiz.getId() != null) {
            logger.error("Attempted to create a Quiz, but id attribute was not null.");
            throw new EntityExistsException(
                    "Cannot create new Quiz with supplied id. The id attribute must be null to create an entity.");
        }

        prepareSections(quiz);
        final Quiz savedQuiz = quizRepository.save(quiz);
        return savedQuiz;
    }

    private void prepareSections(Quiz quiz) {
        List<QuizSection> sections = quiz.getSections();
        for (QuizSection section : sections) {
            section.setQuiz(quiz);
            prepareQuestion(section, section.getQuizQuestions());
        }
    }

    private void prepareQuestion(QuizSection section, List<QuizQuestion> quizQuestions) {
        for (QuizQuestion quizQuestion : quizQuestions) {
            quizQuestion.setSection(section);
        }
    }

    @Override
    public void delete(final Long id) {
        quizRepository.delete(id);
    }

    @Override
    public QuizQuestion updateQuestion(Long id, QuizQuestion question) {
        QuizQuestion questionToUpdate = quizQuestionRepository.findOne(id);
        if (questionToUpdate == null) {
            logger.error("Attempted to update Question, but Question is not found");
            throw new NoResultException(
                    "Cannot update Question with supplied id. The object is not found.");
        }

        questionToUpdate.setQuestion(question.getQuestion());
        questionToUpdate.setAnswers(question.getAnswers());
        return quizQuestionRepository.save(questionToUpdate);
    }

    @Override
    public QuizQuestion answerQuestion(Long id, List<QuizAnswer> quizAnswers) {
        QuizQuestion questionToUpdate = quizQuestionRepository.findOne(id);
        if (questionToUpdate == null) {
            logger.error("Attempted to answer Question, but Question is not found");
            throw new NoResultException(
                    "Cannot answer Question with supplied id. The object is not found.");
        }
        questionToUpdate.setAnswers(quizAnswers);
        questionToUpdate.setUpdateCount(questionToUpdate.getUpdateCount() + 1);
        QuizQuestion savedQuestion = quizQuestionRepository.save(questionToUpdate);
        return savedQuestion;
    }

    @Override
    public QuizQuestion getQuestion(Long id) {
        QuizQuestion questions = quizQuestionRepository.findOne(id);
        if (questions == null) {
            logger.error("Attempted to answer Question, but Question is not found");
            throw new NoResultException(
                    "Cannot answer Question with supplied id. The object is not found.");
        }
        return questions;
    }

    @Override
    public List<QuizQuestion> updateQuestions(List<QuizQuestion> quizQuestions) {
        List<QuizQuestion> questionsToUpdate = Lists.newArrayList();
        for (QuizQuestion quizQuestion : quizQuestions) {
            QuizQuestion questionToUpdate = quizQuestionRepository.findOne(quizQuestion.getId());
            questionToUpdate.setQuestion(quizQuestion.getQuestion());
            questionsToUpdate.add(questionToUpdate);
        }
        List<QuizQuestion> updatedQuizQuestions = quizQuestionRepository.save(questionsToUpdate);
        return updatedQuizQuestions;
    }

    @Override
    public List<QuizQuestion> getQuestions(Long filterBySectionId) {
        if (filterBySectionId == null) {
            return quizQuestionRepository.findAll();
        }
        List<QuizQuestion> quizQuestions = quizQuestionRepository.findAllBySectionId(filterBySectionId);
        return quizQuestions;
    }
}
