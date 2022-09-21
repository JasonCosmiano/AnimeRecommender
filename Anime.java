import java.util.Arrays;
import java.util.List;

/**
 * Anime Class
 * @author Jason Cosmiano
 */
public class Anime implements Comparable<Anime>{

    // attributes
    private int id;
    private String name;
    private String genre;
    private double rating;

    public Anime() {} // constructor

    /**
     * Constructor
     * @param id 
     * @param name anime title
     * @param genre 
     * @param rating 
     */
    public Anime (int id, String name, String genre, double rating) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.rating = rating;
    }

    /**
     * Getter for id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for name
     * @return title of anime
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for rating
     * @return anime rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * Getter for genre
     * @return list of genres
     */
    public List<String> getGenre() {
        if ( genre == null || genre.equals("")) {
            return Arrays.asList();
        }

        String[] genreArray = genre.split(", ");
        List<String> genreList = Arrays.asList(genreArray);
        return genreList;
    }

    /**
     * Setter for genre
     * @param genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Setter for id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter for name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for rating
     * @param rating
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public int compareTo(Anime other) {
        if (other.rating - rating > 0) {
            return -1;
        }

        else if (other.rating - rating == 0) {
            return 0;
        }

        return 1;
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }

}
