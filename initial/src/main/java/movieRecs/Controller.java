package movieRecs;

import a2.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;

@CrossOrigin
@RestController
public class Controller {
    private MovieRatings mr = MovieRatings.getInstance();
    private LoadDataset ld = new LoadDataset(mr, "");
    private RecommendationsController ctrl = new RecommendationsController(mr);


    @RequestMapping("/")
    public String index(@RequestParam(value="algo") String algo,
                        @RequestParam(value="user") String user,
                        @RequestParam(value="recs") String recs,
                        @RequestParam(value="norm", defaultValue ="false") String norm) {
        User u = mr.getUser(user);
        Algo a = Algo.valueOf(algo);
        ctrl.weightedScores(u, a);

        if (norm.equals("true")) {
            HashMap<Double, ArrayList<Movie>> normalized = ctrl.normalizeScores(u, a);
            u.setRecommendation(a, normalized);
        }

        return u.recommendationString(a, Integer.parseInt(recs));
    }

}