import org.javatuples.Triplet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SocialNetworkingShould {

    @Mock
    Console console;

    @Mock
    DateService dateService;

    private SocialNetworking socialNetworking;

    @BeforeEach
    void setUp() {
        socialNetworking = new SocialNetworking(console, dateService);
    }

    @Test
    @Disabled
    void display_on_console_acceptance_test() {
        String exampleUsername = "Alice";
        String exampleMessage = "I love the weather today";
        Instant exampleDate = Instant.parse("1995-10-23T10:12:35Z");
        String minutesAgo = "5 minutes ago";
        Post examplePost =
                new Post(exampleDate, exampleUsername, exampleMessage);
        String messageToConsole = "Alice - I love the weather today (5 minutes ago)";

//        when(dateService.getTimeFromPosting(examplePost))
//                .thenReturn(minutesAgo);
        socialNetworking.post(exampleUsername, exampleMessage);
        socialNetworking.getWall(exampleUsername);
        verify(console).message(messageToConsole);

        socialNetworking.getWall("Charlie");
        verify(console, times(1)).message(messageToConsole);

        socialNetworking.follow("Charlie", exampleUsername);

        socialNetworking.getWall("Charlie");
        verify(console, times(2)).message(messageToConsole);
    }

    @Test
    void post_should_save_the_message() {
        String exampleUsername = "Alice";
        String exampleMessage = "I love the weather today";
        Instant exampleDate = Instant.parse("1995-10-23T10:12:35Z");
        Post examplePost =
                new Post(exampleDate, exampleUsername, exampleMessage);
        when(dateService.getCurrentTime())
                .thenReturn(exampleDate);
        socialNetworking.post(exampleUsername, exampleMessage);
        assertTrue(socialNetworking.messageStore.contains(examplePost));
    }

    @Test
    void does_getWall_display_a_post() {
        String messageToConsole = "Alice - I love the weather today (5 minutes ago)";
        String exampleUsername = "Alice";
        String exampleMessage = "I love the weather today";

        Instant postDate = Instant.parse("2019-11-19T10:12:00Z");
        Instant nowDate = Instant.parse("2019-11-19T10:17:00Z");
        when(dateService.getCurrentTime()).thenReturn(postDate).thenReturn(nowDate);
        socialNetworking.post(exampleUsername, exampleMessage);

        socialNetworking.getWall(exampleUsername);

        verify(console).message(messageToConsole);
    }

    @Test
    void does_getWall_display_multiple_posts() {
        String firstMessageToConsole = "Alice - I love the weather today (5 minutes ago)";
        String secondMessageToConsole = "Alice - I'm in New York today! (5 minutes ago)";
        String messageUsername = "Alice";
        String firstMessage = "I love the weather today";
        String secondMessage = "I'm in New York today!";

        Instant postDate = Instant.parse("2019-11-19T10:12:00Z");
        Instant nowDate = Instant.parse("2019-11-19T10:17:00Z");
        when(dateService.getCurrentTime()).thenReturn(postDate).thenReturn(postDate).thenReturn(nowDate);

        socialNetworking.post(messageUsername, firstMessage);
        socialNetworking.post(messageUsername, secondMessage);
        socialNetworking.getWall(messageUsername);

        verify(console).message(firstMessageToConsole);
        verify(console).message(secondMessageToConsole);
    }

    @Test
    void getWall_shows_posts_from_current_user() {
        String messageToConsole = "Alice - I love the weather today (5 minutes ago)";
        String messageUsername = "Alice";
        String wallUsername = "Charlie";
        String exampleMessage = "I love the weather today";

        Instant postDate = Instant.parse("2019-11-19T10:12:00Z");
        Instant nowDate = Instant.parse("2019-11-19T10:17:00Z");
        when(dateService.getCurrentTime()).thenReturn(postDate).thenReturn(nowDate);

        socialNetworking.post(messageUsername, exampleMessage);
        socialNetworking.getWall(wallUsername);

        verify(console, never()).message(messageToConsole);
    }

    @Test
    void getTimeFromPost_returns_5mins_when_5mins_ago() {
        String exampleUsername = "Alice";
        String exampleMessage = "I love the weather today";
        Instant postDate = Instant.parse("2019-11-19T10:12:00Z");
        Instant nowDate = Instant.parse("2019-11-19T10:17:00Z");
        Post examplePost = new Post(postDate, exampleUsername, exampleMessage);
        when(dateService.getCurrentTime()).thenReturn(nowDate);

        var result = examplePost.getTimeFromDate(dateService.getCurrentTime());

        assertEquals("(5 minutes ago)", result);
    }

    @Test
    void getTimeFromPost_returns_10mins_when_10mins_ago() {
        String exampleUsername = "Alice";
        String exampleMessage = "I love the weather today";
        Instant postDate = Instant.parse("2019-11-19T10:12:00Z");
        Instant nowDate = Instant.parse("2019-11-19T10:22:00Z");
        Post examplePost = new Post(postDate, exampleUsername, exampleMessage);
        when(dateService.getCurrentTime()).thenReturn(nowDate);

        var result = examplePost.getTimeFromDate(dateService.getCurrentTime());

        assertEquals("(10 minutes ago)", result);
    }

}
