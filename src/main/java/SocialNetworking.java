import java.util.ArrayList;
import java.util.List;

public class SocialNetworking {

    public List<Post> messageStore;
    private Console console;
    private DateService dateService;

    public SocialNetworking(Console console, DateService dateService) {
        this.console = console;
        this.dateService = dateService;
        messageStore = new ArrayList<>();
    }

    public void post(String name, String message) {
        messageStore.add(new Post(dateService.getCurrentTime(), name, message));
    }

    public void getWall(String username) {
        for (Post post : messageStore) {
            if (post.isUsername(username)) {
                console.message(post.toString(dateService.getCurrentTime()));
            }
        }
    }

    public void follow(String personFollowing, String personToBeFollowed) {
        throw new UnsupportedOperationException();
    }
}
