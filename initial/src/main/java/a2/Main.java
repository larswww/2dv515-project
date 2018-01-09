package a2;

public class Main {

    public static void main(String[] args) {
	// write your code here
        MovieRatings mr = MovieRatings.getInstance();
        SeedScript sr = new SeedScript(mr);
        sr.lectureData();
        RecommendationsController ctrl = new RecommendationsController(mr);
        ctrl.calculateForAll();


        // add users
        // add movies

    }
}
