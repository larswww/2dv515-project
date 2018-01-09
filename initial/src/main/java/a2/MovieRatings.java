package a2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovieRatings {
    private static MovieRatings movieRatings = new MovieRatings();
    private HashMap<String, Movie> movieDb = new HashMap<>();
    private HashMap<String, User> userDb = new HashMap<>();

    private MovieRatings() {

    }

    public static MovieRatings getInstance() {
        return movieRatings;
    }

    public Map<String, Movie> movieDb() { return movieDb; }

    public HashMap<String, User> userDb() { return userDb; }

    public void addUser(User u) {
        if (userDb.get(u.id()) != null) return;
        userDb.put(u.id(), u);
    }

    public User getUser(String id) {
        return userDb.get(id);
    }

    public void addMovie(Movie m) {
        if (movieDb.get(m.id()) != null) return;

        movieDb.put(m.id(), m);
    }

    public Movie getMovie(String title) {
        return movieDb.get(title);
    }
}
