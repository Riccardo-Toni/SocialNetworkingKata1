
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SocialNetworkingShould {

    private SocialNetworking socialNetworking;
    @Mock
    Console console;

    @Test
    void save_the_post_when_user_post_something() {
        socialNetworking.post("Alice", "first message");
        verify(console).message("Alice", "first message");
    }
}
