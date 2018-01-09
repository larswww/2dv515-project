package a2;

import movieRecs.LoadDataset;
import org.junit.Before;
import org.junit.Test;


import java.util.*;

import static junit.framework.TestCase.assertEquals;


public class TestCalculations {
    private MovieRatings mr;
    private LoadDataset ld;
    private RecommendationsController ctrl;

    @Before
    public void SetupLectureExample() {
        mr = MovieRatings.getInstance();
        ld = new LoadDataset(mr, "test");
        ctrl = new RecommendationsController(mr);
    }

    @Test
    public void SimpleAverageNotSeen() {


    }

    @Test
    public void LectureEuclideanDistance() {
        User toby = mr.getUser("Toby");
        ctrl.calculateFor(toby, Algo.EUCLIDEAN_DISTANCE);
        List<Score> euclidean = toby.getAlgoScores(Algo.EUCLIDEAN_DISTANCE);

        assertEquals(0.3076923076923077, euclidean.get(0).score());
        assertEquals(0.23529411764705882, euclidean.get(1).score());
    }

    @Test
    public void LecturePearsonCorrelation() {
        User toby = mr.getUser("Toby");
        ctrl.calculateFor(toby, Algo.PEARSON_CORRELATION);
        List<Score> pearson = toby.getAlgoScores(Algo.PEARSON_CORRELATION);

        assertEquals(0.9912407071619299, pearson.get(0).score());
        assertEquals(0.9244734516419049, pearson.get(1).score());
        assertEquals(0.8934051474415647, pearson.get(2).score());
    }

    @Test
    public void LectureWeightedPearson() {
        User u = mr.getUser("Toby");
        Algo a = Algo.valueOf("PEARSON_CORRELATION");
        ctrl.weightedScores(u, a);
       System.out.println(u.recommendationString(a, 50));
       System.out.println("Expected: WS Night 3.35, WS Lady 2.83, WS Luck 2.53");

        HashMap<Double, ArrayList<Movie>> normalisedPearson = ctrl.normalizeScores(u, a);
        u.setRecommendation(a, normalisedPearson);
        System.out.println(u.recommendationString(a, 50));



        a = Algo.valueOf("EUCLIDEAN_DISTANCE");
       ctrl.weightedScores(u, a);
       System.out.println(u.recommendationString(a, 50));
       System.out.println("Expected: WS Night 3.35, WS Lady 2.85, Ws Luck 2.45");

        HashMap<Double, ArrayList<Movie>> normaliszedEuclidean = ctrl.normalizeScores(u, a);
        u.setRecommendation(a, normaliszedEuclidean);
        System.out.println(u.recommendationString(a, 50));

    }


}
