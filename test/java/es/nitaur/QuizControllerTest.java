package es.nitaur;

import com.google.common.collect.Lists;
import es.nitaur.domain.Quiz;
import es.nitaur.domain.QuizAnswer;
import es.nitaur.domain.QuizQuestion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuizControllerTest {

    private static final String UPDATE_QUESTION_BY_FIXED_ID_API = "/api/quiz/updateQuestion/1";
    private static final String ANSWER_QUESTION_BY_FIXED_ID_API = "/api/quiz/answerQuestion/1";
    private static final String GET_ALL_QUESTIONS_API = "/api/quiz/allQuestions";
    private static final String GET_QUESTION_BY_ID_API = "/api/quiz/getQuestion/";
    private static final String GET_QUIZ_BY_ID_API = "/api/quiz/";
    private static final String GET_QUIZZES_API = "/api/quiz";
    private static final String QUESTION = "Question";
    private static final String ANSWER = "Answer";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAllQuizzesTest() {
        ResponseEntity<List<Quiz>> response = restTemplate.exchange(GET_QUIZZES_API, HttpMethod.GET, null, new ParameterizedTypeReference<List<Quiz>>() {});
        List<Quiz> quizzes = response.getBody();

        assertThat("Quizzes count is 1", 1, is(quizzes.size()));
    }

    @Test
    public void getQuizByIdTest() {
        Quiz quizFromDB = restTemplate.getForObject(GET_QUIZ_BY_ID_API + 1, Quiz.class);

        assertThat("Quiz's id must be equals " + quizFromDB.getId(), quizFromDB.getId(), is(1L));
    }

    @Test
    public void getQuizByIdTest_wrongId() {
        Quiz quizFromDB = restTemplate.getForObject(GET_QUIZ_BY_ID_API + 0, Quiz.class);

        assertThat("Quiz mustn't exist", null, is(quizFromDB));
    }

    @Test
    public void quizIsNotSavedWithNotNullId() {
        long id = 999L;
        Quiz quiz = new Quiz();
        quiz.setName("name");
        quiz.setId(id);

        restTemplate.postForLocation(GET_QUIZZES_API, quiz);

        Quiz quizFromDB = restTemplate.getForObject(GET_QUIZ_BY_ID_API + id, Quiz.class);

        assertThat("Quiz must be null", null, is(quizFromDB));
    }

    @Test
    public void updateQuestionTest() {
        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setQuestion(QUESTION);
        quizQuestion.setAnswers(Lists.newArrayList());

        restTemplate.postForLocation(UPDATE_QUESTION_BY_FIXED_ID_API, quizQuestion);

        QuizQuestion question = restTemplate.getForObject(GET_QUESTION_BY_ID_API + 1, QuizQuestion.class);
        assertThat("Question text is " + QUESTION, QUESTION, is(question.getQuestion()));
    }

    @Test
    public void getAllQuestionsTest() {
        ResponseEntity<List<QuizQuestion>> exchange = restTemplate.exchange(GET_ALL_QUESTIONS_API, HttpMethod.GET, null, new ParameterizedTypeReference<List<QuizQuestion>>() {});

        List<QuizQuestion> questions = exchange.getBody();
        assertThat("There are 4 questions", 4, is(questions.size()));
    }

    @Test
    public void answerQuestionTest() {
        QuizAnswer answer = new QuizAnswer();
        answer.setAnswer(ANSWER);

        restTemplate.postForLocation(ANSWER_QUESTION_BY_FIXED_ID_API, Lists.newArrayList(answer));

        QuizQuestion question = restTemplate.getForObject(GET_QUESTION_BY_ID_API + 1, QuizQuestion.class);
        assertThat("There are updated answers", ANSWER, is(question.getAnswers().get(0).getAnswer()));
    }

}
