package es.nitaur.service.impl;

import es.nitaur.domain.Quiz;
import es.nitaur.domain.QuizQuestion;
import es.nitaur.domain.QuizSection;
import es.nitaur.service.QuizService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static es.nitaur.utils.PrepareDomainObjects.ANSWER;
import static es.nitaur.utils.PrepareDomainObjects.prepareQuiz;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuizServiceImplTest {
    private static final String GET_QUIZZES_API = "/api/quiz";

    @Autowired
    private QuizService quizService;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createQuiz() {
        Quiz quiz = prepareQuiz();

        Quiz quizFromDB = restTemplate.postForObject(GET_QUIZZES_API, quiz, Quiz.class);

        assertThat("Quiz must be created", null, not(quizFromDB));
        assertThat("Created quiz id is not null", null, not(quizFromDB.getId()));

        Quiz quizFromDBWithAllRelations = quizService.findOne(quizFromDB.getId());

        QuizSection quizSection = quizFromDBWithAllRelations.getSections().get(0);
        QuizQuestion quizQuestion = quizSection.getQuizQuestions().get(0);

        assertThat(quizSection.getQuiz(), is(quizFromDBWithAllRelations));
        assertThat(quizQuestion.getSection(), is(quizSection));

        assertThat(quizQuestion.getAnswers().get(0).getAnswer(), is(ANSWER));

        quizService.delete(quizFromDBWithAllRelations.getId());
    }
}