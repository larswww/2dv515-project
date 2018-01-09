package movieRecs;

import a2.Movie;
import a2.MovieRatings;
import a2.User;
import a2.Rating;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class LoadDataset {
    private MovieRatings mr;
    private String dataSetPath = "/Users/mbp/Documents/Code/2dv515/Project/initial/data/";

    public LoadDataset(MovieRatings mr, String prefix) {
        this.mr = mr;
        dataSetPath += prefix;
        movies();
        users();
    }

    public void addUserRating(String uid, String mid, Double rating) {
        User u = mr.getUser(uid);
        if (u == null) {
            u = new User(uid);
            mr.addUser(u);
        }

        Movie m = mr.getMovie(mid);
        Rating r = new Rating(m, u, rating);
        m.linkRatings(r, u);

    }

    private void loadFilesToMemory(){}

    private void movies(){
        String movieFile = dataSetPath + "movies.csv";
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(movieFile))) {
            br.readLine();

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] movie = line.split(",");
                String genres = movie[2];
                Movie m = new Movie(movie[1], movie[0], genres);
                mr.addMovie(m);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void users(){
        String ratingsFile = dataSetPath + "ratings.csv";
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(ratingsFile))) {
            br.readLine();

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] rating = line.split(","); // userId,movieId,rating,timestamp
                String userId = rating[0];
                String movieId = rating[1];
                double score = Double.parseDouble(rating[2]);
                //todo save timestamp if you want?
                User u = mr.getUser(userId);
                if (u == null) {
                    u = new User(userId);
                    mr.addUser(u);
                }

                Movie m = mr.getMovie(movieId);
                Rating r = new Rating(m, u, score);
                m.linkRatings(r, u);


            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    private void ratings(){}


}
