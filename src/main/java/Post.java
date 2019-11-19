import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class Post {
    private Instant date;
    private String username;
    private String message;

    public Post(Instant date, String username, String message) {
        this.date = date;
        this.username = username;
        this.message = message;
    }

    public boolean isUsername(String username) {
        return this.username.equals(username);
    }

    String getTimeFromDate(Instant now) {
        Duration between = Duration.between(now, date);
        return "(" + between.abs().toMinutes() + " minutes ago)";
    }

    public String toString(Instant now) {
        return username + " - " + message + " " + getTimeFromDate(now);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(date, post.date) &&
                Objects.equals(username, post.username) &&
                Objects.equals(message, post.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, username, message);
    }
}
