import java.util.Arrays;
import java.util.List;

public class Anime implements Comparable<Anime>{

    private int id;
    private String name;
    private String genre;
    private double rating;

    public Anime() {}

    public Anime (int id, String name, String genre, double rating) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public List<String> getGenre() {
        if ( genre == null || genre.equals("")) {
            return Arrays.asList();
        }

        String[] genreArray = genre.split(", ");
        List<String> genreList = Arrays.asList(genreArray);
        return genreList;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

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
