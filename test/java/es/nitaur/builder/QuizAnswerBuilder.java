package es.nitaur.builder;

import es.nitaur.domain.QuizAnswer;
import es.nitaur.domain.QuizQuestion;

public final class QuizAnswerBuilder {
    private QuizAnswer quizAnswer;

    private QuizAnswerBuilder() {
        quizAnswer = new QuizAnswer();
    }

    public static QuizAnswerBuilder aQuizAnswer() {
        return new QuizAnswerBuilder();
    }

    public QuizAnswerBuilder withId(Long id) {
        quizAnswer.setId(id);
        return this;
    }

    public QuizAnswerBuilder withAnswer(String answer) {
        quizAnswer.setAnswer(answer);
        return this;
    }

    public QuizAnswerBuilder withQuestion(QuizQuestion question) {
        quizAnswer.setQuestion(question);
        return this;
    }

    public QuizAnswer build() {
        return quizAnswer;
    }
}
