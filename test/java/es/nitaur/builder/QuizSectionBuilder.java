package es.nitaur.builder;

import es.nitaur.domain.Quiz;
import es.nitaur.domain.QuizSection;
import org.assertj.core.util.Lists;

public final class QuizSectionBuilder {
    private QuizSection quizSection;

    private QuizSectionBuilder() {
        quizSection = new QuizSection();
    }

    public static QuizSectionBuilder aQuizSection() {
        return new QuizSectionBuilder();
    }

    public QuizSectionBuilder withId(Long id) {
        quizSection.setId(id);
        return this;
    }

    public QuizSectionBuilder withQuizQuestion(QuizQuestionBuilder questionBuilder) {
        quizSection.setQuizQuestions(Lists.newArrayList(questionBuilder.build()));
        return this;
    }

    public QuizSectionBuilder withQuiz(Quiz quiz) {
        quizSection.setQuiz(quiz);
        return this;
    }

    public QuizSection build() {
        return quizSection;
    }
}
