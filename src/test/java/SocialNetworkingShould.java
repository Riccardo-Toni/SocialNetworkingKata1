import org.javatuples.Triplet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SocialNetworkingShould {

    @Mock
    Console console;

    @Test
    void save_the_post_when_user_post_something() {

        String exampleUsername = "Alice";
        String exampleMessage = "first message";
        Instant exampleDate = Instant.parse("1995-10-23T10:12:35Z");

        SocialNetworking socialNetworking = new SocialNetworking();

        socialNetworking.post(exampleUsername, exampleMessage);
        verify(console).message(exampleUsername, exampleMessage);

        Triplet<Instant, String, String> examplePost =
                new Triplet<>(exampleDate, exampleUsername, exampleMessage);

        verify(socialNetworking).messageStore.contains(examplePost);

        // still need to verify
            // time ago (5 minutes etc)
            // following
                // user only sees followed users
    }
}
