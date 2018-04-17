package es.nitaur.builder;

import com.google.common.collect.Lists;
import es.nitaur.domain.Quiz;

public final class QuizBuilder {
    private Quiz quiz;

    private QuizBuilder() {
        quiz = new Quiz();
    }

    public static QuizBuilder aQuiz() {
        return new QuizBuilder();
    }

    public QuizBuilder withId(Long id) {
        quiz.setId(id);
        return this;
    }

    public QuizBuilder withName(String name) {
        quiz.setName(name);
        return this;
    }

    public QuizBuilder withSection(QuizSectionBuilder builder) {
        quiz.setSections(Lists.newArrayList(builder.build()));
        return this;
    }

    public Quiz build() {
        return quiz;
    }
}
