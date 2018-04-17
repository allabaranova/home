package es.nitaur.builder;

import es.nitaur.domain.QuizQuestion;
import es.nitaur.domain.QuizSection;
import org.assertj.core.util.Lists;

public final class QuizQuestionBuilder {
    private QuizQuestion quizQuestion;

    private QuizQuestionBuilder() {
        quizQuestion = new QuizQuestion();
    }

    public static QuizQuestionBuilder aQuizQuestion() {
        return new QuizQuestionBuilder();
    }

    public QuizQuestionBuilder withId(Long id) {
        quizQuestion.setId(id);
        return this;
    }

    public QuizQuestionBuilder withQuestion(String question) {
        quizQuestion.setQuestion(question);
        return this;
    }

    public QuizQuestionBuilder withUpdateCount(Long updateCount) {
        quizQuestion.setUpdateCount(updateCount);
        return this;
    }

    public QuizQuestionBuilder withAnswer(QuizAnswerBuilder answerBuilder) {
        quizQuestion.setAnswers(Lists.newArrayList(answerBuilder.build()));
        return this;
    }

    public QuizQuestionBuilder withSection(QuizSection section) {
        quizQuestion.setSection(section);
        return this;
    }

    public QuizQuestion build() {
        return quizQuestion;
    }
}
