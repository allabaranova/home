package es.nitaur.service.impl;

import es.nitaur.domain.Quiz;
import es.nitaur.service.QuizService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static es.nitaur.builder.QuizAnswerBuilder.aQuizAnswer;
import static es.nitaur.builder.QuizBuilder.aQuiz;
import static es.nitaur.builder.QuizQuestionBuilder.aQuizQuestion;
import static es.nitaur.builder.QuizSectionBuilder.aQuizSection;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuizServiceImplTest {
    private static final String GET_QUIZZES_API = "/api/quiz";

    private static final String QUESTION = "Question";
    private static final String ANSWER = "Answer";
    private static final String QUIZ = "Quiz";

    @Autowired
    private QuizService quizService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createQuiz() {
        Quiz quiz = prepareQuiz();

        restTemplate.postForLocation(GET_QUIZZES_API, quiz);

        Quiz quizFromDB = quizService.findOne(2L);
        assertThat("Created quiz id is not null", null, not(quizFromDB.getId()));

        assertThat(quizFromDB.getSections().get(0).getQuiz(), is(quizFromDB));
        assertThat(quizFromDB.getSections().get(0).getQuizQuestions().get(0).getSection(), is(quizFromDB.getSections().get(0)));

        assertThat(quizFromDB.getSections().get(0).getQuizQuestions().get(0).getAnswers().get(0).getAnswer(), is(ANSWER));
    }

    private Quiz prepareQuiz() {
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


}