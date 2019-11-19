import org.javatuples.Triplet;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class SocialNetworking {

    public List<Triplet<Instant, String, String>> messageStore;
    private Console console;
    private DateService dateService;

    public SocialNetworking(Console console, DateService dateService) {
        this.console = console;
        this.dateService = dateService;
        messageStore = new ArrayList<>();
    }

    public void post(String name, String message) {
        messageStore.add(new Triplet<>(dateService.getCurrentTime(),
                name, message));
    }

    public void getWall(String username) {
        String messageToConsole = username +
                " - " +
                messageStore.get(0).getValue(2) +
                " (5 minutes ago)";
        console.message(messageToConsole);
    }

    public void follow(String personFollowing, String personToBeFollowed) {
        throw new UnsupportedOperationException();
    }
}
