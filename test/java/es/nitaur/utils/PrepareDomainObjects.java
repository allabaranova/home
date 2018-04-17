package es.nitaur.utils;

import es.nitaur.domain.Quiz;
import es.nitaur.domain.QuizQuestion;

import static es.nitaur.builder.QuizAnswerBuilder.aQuizAnswer;
import static es.nitaur.builder.QuizBuilder.aQuiz;
import static es.nitaur.builder.QuizQuestionBuilder.aQuizQuestion;
import static es.nitaur.builder.QuizSectionBuilder.aQuizSection;

public class PrepareDomainObjects {
    public static final String QUESTION = "Question";
    public static final String ANSWER = "Answer";
    public static final String QUIZ = "Quiz";

    public static Quiz prepareQuiz() {
        Quiz quiz = aQuiz()
                .withName(QUIZ)
                .withSection(
                        aQuizSection()
                                .withQuizQuestion(
                                        aQuizQuestion()
                                                .withQuestion(QUESTION)
                                                .withAnswer(
                                                        aQuizAnswer()
                                                                .withAnswer(ANSWER))))
                .build();
        return quiz;
    }

    public static QuizQuestion prepareQuizQuestion() {
        QuizQuestion quizQuestion = aQuizQuestion()
                                                .withQuestion(QUESTION)
                                                .withAnswer(
                                                        aQuizAnswer()
                                                                .withAnswer(ANSWER))
                .build();
        return quizQuestion;
    }
}
