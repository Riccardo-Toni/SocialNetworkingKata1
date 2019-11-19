import org.javatuples.Triplet;

import java.time.Instant;
import java.util.List;

public class SocialNetworking {

    public List<Triplet<Instant, String, String>> messageStore;
    private Console console;
    private DateService dateService;

    public SocialNetworking(Console console, DateService dateService) {
        this.console = console;
        this.dateService = dateService;
    }

    public void post(String alice, String first_message) {
        throw new UnsupportedOperationException();
    }

    public void getWall(String username) {
        throw new UnsupportedOperationException();
    }

    public void follow(String personFollowing, String personToBeFollowed) {
        throw new UnsupportedOperationException();
    }
}
