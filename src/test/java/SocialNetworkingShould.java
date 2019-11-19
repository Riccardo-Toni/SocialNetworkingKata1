import org.javatuples.Triplet;
import org.junit.jupiter.api.BeforeEach;
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
    void display_on_console_acceptance_test() {
        String exampleUsername = "Alice";
        String exampleMessage = "I love the weather today";
        Instant exampleDate = Instant.parse("1995-10-23T10:12:35Z");
        String minutesAgo = "5 minutes ago";
        Triplet<Instant, String, String> examplePost =
                new Triplet<>(exampleDate, exampleUsername, exampleMessage);
        String messageToConsole = "Alice - I love the weather today (5 minutes ago)";

        when(dateService.getTimeFromPosting(examplePost))
                .thenReturn(minutesAgo);
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
        Triplet<Instant, String, String> examplePost =
                new Triplet<>(exampleDate, exampleUsername, exampleMessage);
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
        Instant exampleDate = Instant.parse("1995-10-23T10:12:35Z");
        Triplet<Instant, String, String> examplePost =
                new Triplet<>(exampleDate, exampleUsername, exampleMessage);
        socialNetworking.post(exampleUsername, exampleMessage);
        socialNetworking.getWall(exampleUsername);
        verify(console).message(messageToConsole);
    }
}
