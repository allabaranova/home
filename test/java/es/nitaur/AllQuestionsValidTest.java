package es.nitaur;

import com.google.common.collect.Lists;
import es.nitaur.domain.QuizQuestion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AllQuestionsValidTest {

    public static final String UPDATE_QUESTION_API = "/api/quiz/updateQuestions";
    public static final String GET_ALL_QUESTIONS_API = "/api/quiz/allQuestions";
    public static final String REDACTED = "<<redacted>>";

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void questionsAreNotSavedWithEmptyQuestionText() {
        QuizQuestion quizQuestion1 = createQuizQuestion(1L, REDACTED);
        QuizQuestion quizQuestion2 = createQuizQuestion(2L, null);

        List<QuizQuestion> questionsToUpdate = Lists.newArrayList(quizQuestion1, quizQuestion2);

        restTemplate.postForLocation(UPDATE_QUESTION_API, questionsToUpdate);

        ResponseEntity<List<QuizQuestion>> exchange = restTemplate.exchange(GET_ALL_QUESTIONS_API + "?filterSectionId=1", HttpMethod.GET, null, new ParameterizedTypeReference<List<QuizQuestion>>() {});
        List<QuizQuestion> quizQuestions = exchange.getBody();

        for (QuizQuestion quizQuestion : quizQuestions) {
            assertThat("Question text should not be " + REDACTED, REDACTED, not(quizQuestion.getQuestion()));
        }
    }

    @Test
    public void questionsAreSavedWithQuestionText() {
        QuizQuestion quizQuestion3 = createQuizQuestion(3L, REDACTED);
        QuizQuestion quizQuestion4 = createQuizQuestion(4L, REDACTED);

        List<QuizQuestion> questionsToUpdate = Lists.newArrayList(quizQuestion3, quizQuestion4);

        restTemplate.postForLocation(UPDATE_QUESTION_API, questionsToUpdate);

        ResponseEntity<List<QuizQuestion>> exchange = restTemplate.exchange(GET_ALL_QUESTIONS_API + "?filterSectionId=2", HttpMethod.GET, null, new ParameterizedTypeReference<List<QuizQuestion>>() {});
        List<QuizQuestion> quizQuestions = exchange.getBody();

        for (QuizQuestion quizQuestion : quizQuestions) {
            assertThat("Question text is only " + REDACTED, REDACTED, is(quizQuestion.getQuestion()));
        }
    }

    private QuizQuestion createQuizQuestion(long id, String question) {
        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setId(id);
        quizQuestion.setQuestion(question);
        return quizQuestion;
    }
}
