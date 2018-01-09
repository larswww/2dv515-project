package a2;

import java.util.*;

public class RecommendationsController {
    private MovieRatings mr;

    public RecommendationsController(MovieRatings mr) {
        this.mr = mr;
//        calculateForAll();
    }

    // for running all defined algos on all users in db
    public void calculateForAll() {

        System.out.println("calculating for " + mr.userDb().size() + " users");
        for (Algo a : Algo.values()) {
            mr.userDb().forEach((k, v) -> calculateFor(v, a));
        }
        System.out.print("all users calculated");
    }

    public void calculateFor(User u, Algo a) {
        ArrayList<Score> result = new ArrayList<>();

        if (a == Algo.EUCLIDEAN_DISTANCE) {
            new EuclideanDistance(result, u, mr.userDb());
        }

        if (a == Algo.PEARSON_CORRELATION) {
            new PearsonCorrelation(result, u, mr.userDb());
        }


        System.out.println(u.toString());
        u.setAlgoScore(a, result);

    }

    public void weightedScores(User u, Algo a) {
        if (u.unseen().size() == 0) mr.movieDb().forEach((k, v) -> u.addUnseen(v));
        u.setRecommendation(a, CalculatedWeightedScores(u, a));
    }


    public HashMap<Double, ArrayList<Movie>> CalculatedWeightedScores(User user, Algo algo) {
        List<Score> userSimilarityScores = user.getAlgoScores(algo);
        if (userSimilarityScores == null) {
            calculateFor(user, algo);
            userSimilarityScores = user.getAlgoScores(algo);
        }
        HashMap<Double, ArrayList<Movie>> movieScores = new HashMap<>();

        for (Movie m : user.unseen()) {
            if (m.toString().equals("Hands on a Hard Body (1996)")) {
                System.out.println("now");
            }
            double sum = 0;
            double sum_sim = 0;
            double max = Double.MIN_VALUE;
            int raters = 0; //todo ? this should be a thing no?

            for (Score s : userSimilarityScores) {
                User u = s.user();

                double userScore = u.getMovieRating(m);
                if (userScore != -1) {
                    if (userScore > max) max = userScore; // save the max for normalization
                    sum += (userScore * s.score());
                    sum_sim += s.score();
                    raters++;
                }
            }

            if (raters != 0) { // to avoid calculating on/putting in movies that nobody has rated

                if (sum == 0.0) sum = 0.00001;
                if (algo == Algo.PEARSON_CORRELATION) {
                    sum = sum / max * 5.0;
                } else {
                    if (sum_sim == 0.0) sum_sim = 0.00001;
                    sum = sum / sum_sim;
                }

                movieScores.putIfAbsent(sum, new ArrayList<Movie>());
                movieScores.get(sum).add(m);
            }


        }
        return movieScores;
    }

    public HashMap<Double, ArrayList<Movie>> normalizeScores(User u, Algo a) {
        HashMap<Double, ArrayList<Movie>> ms = u.getRecommendations(a);
        HashMap<Double, ArrayList<Movie>> normalized = new HashMap<>();
        Iterator<Double> scores = new TreeSet<>(ms.keySet()).descendingIterator();
        double adjustment = 1.0;
        if (a == Algo.PEARSON_CORRELATION) adjustment = 5.0;


        // ms.get(Double.POSITIVE_INFINITY); // NEGATIVE_INFINITY // NaN
        double max = Double.MIN_VALUE;

        while (scores.hasNext()) {
            double s = scores.next();
            if (s > max) max = s;
        }

        scores = new TreeSet<>(ms.keySet()).descendingIterator();


        if (max == 0.0) max = 0.00001;
        while (scores.hasNext()) {
            Double score = scores.next();
            if (a == Algo.PEARSON_CORRELATION) {
                normalized.put(score / max * 5.0, ms.get(score));
            } else {
                normalized.put(score / max * 5.0, ms.get(score));
            }
        }

        return normalized;
    }

}
