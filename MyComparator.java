import java.util.Comparator;
import java.util.Map;

/**
 * Comparator for Anime
 * @author Jason Cosmiano
 */
public class MyComparator implements Comparator<Anime> {

    // map <Anime: rating>
    Map<Anime, Integer> map;
    
    /**
     * Constructor
     */
    public MyComparator(Map<Anime, Integer> map) {
        this.map = map;
    }
    
    @Override
    public int compare(Anime o1, Anime o2) {
        if (map.get(o1) >= map.get(o2)) {
            return -1;
        } else {
            return 1;
        }
    }

}