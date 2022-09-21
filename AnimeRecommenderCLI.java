import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Anime Recommender
 * @author Jason Cosmiano
 */
public class AnimeRecommenderCLI {

    // all the themes
    private static final String themes = "Adult Cast, Anthropomorphic, CGDCT, Childcare, Combat Sports, \n"
                                        +  "Crossdressing, Delinquents, Detective, Educational, \n"
                                        + "Gag Humor, Gore, High Stakes Game, Historical, Idols (Female), Idols (Male), \n" 
                                        + "Isekai, Iyashikei, Love Polygon, Magical Sex Shift, Mahou Shoujo, \n"
                                        + "Martial Arts, Mecha, Medical, Military, Music, Mythology, Organized Crime, \n"
                                        + "Otaku Culture, Parody, Performing Arts, Pets, Psychological, Racing, Reincarnation, \n"
                                        + "Romantic Subtext, Samurai, School, Showbiz, Space, Strategy Game, Super Power, \n" 
                                        + "Survival, Team Sports, Time Travel, Vampire, Video Game, Visual Arts, Workplace";

    /*
     * Read the csv file with all the anime
     */
    private static List<Anime> readAnimeCSV() throws NumberFormatException, IOException {

         try (// open file input stream
        BufferedReader reader = new BufferedReader(new FileReader("anime.csv"))) {

            // read file line by line
            String line = null;
            Scanner scanner = null;
            int index = 0;

            List<Anime> listofAnime = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                Anime newAnime = new Anime();
                scanner = new Scanner(line);
            	scanner.useDelimiter(",");

            	while (scanner.hasNext()) {

            		String data = scanner.next();

                    // set anime id
                    if (index == 0 && (!data.equals("anime_id"))) {
                        newAnime.setId(Integer.valueOf(data));
                    }

                    // set anime name
                    else if (index == 1 && (!data.equals("name"))) {
                        newAnime.setName(data);
                    }

                    // set genre
                    else if (index == 2 && (!data.equals("genre") && (!data.equals("Unknown")
                                && !data.equals("") && data != null))) {

                        if (!data.startsWith("\"")) {
                            newAnime.setGenre(data);
                        }

                        else {
                            while (!data.endsWith("\"")) {
                                data += "," + scanner.next();
                                index++;
                            }

                            newAnime.setGenre(data.replaceAll("\"", ""));
                        }
                    }

                    // set rating (second to last in row)
                    else if ( (index == (line.split(",").length - 2)) && (!data.equals("rating")) )  {

                        if ( (data.equals(null)) || (data.equals("")) ) {
                            newAnime.setRating(Double.valueOf(0.00));
                        }
                        
                        else {
                        newAnime.setRating(Double.valueOf(data));
                        }
                    }

                    index++;
                }

                listofAnime.add(newAnime); // list of all anime in the csv file
                index = 0;
            }

            reader.close();

            return listofAnime;
        }
    }

    /**
     * Prevents anime from your favorite franchise from being recommended
     */
    public static boolean containsName(final List<Anime> list, final String name){
        return list.stream().map(Anime::getName).filter(name::equals).findFirst().isPresent();
    }

    /**
     * Getter for list of genres
     * @param list list of anime
     * @return a list of genres
     */
    public static List<List<String>> genresLists(final List<Anime> list) {
        return list.stream()
                   .map(p -> p.getGenre())
                   .collect(Collectors.toList());
    }

    /**
     * Get user anime suggestions
     * @param genresList list of genres
     * @param favString fav anime string
     * @return a list of suggested anime
     * @throws NumberFormatException
     * @throws IOException
     */
    public static List<Anime> getSuggestions(List<String> genresList, String favString) throws NumberFormatException, IOException {

        HashMap<Anime, Integer> map = new HashMap<>();
        List<Anime> listAnime = readAnimeCSV();

        for (Anime anime : listAnime) { 

            int genreScore = 0;
            List<String> genresOfTheAnime = anime.getGenre(); // genre of an anime the in total list

            for (String genre : genresOfTheAnime) {
                if (genresList.contains(genre)) {

                    // themes are more specific/ there's a stronger relation
                    if (themes.contains(genre)) {
                        genreScore += 2 ;
                    }

                    else {
                        genreScore++;
                    }
                }                
            }

            map.put(anime, genreScore);
        }

        // sort by rating
        MyComparator comp = new MyComparator(map);
        TreeMap<Anime, Integer> newMap = new TreeMap<Anime, Integer>(comp);
        newMap.putAll(map);

        List<Anime> suggestionsList = new ArrayList<>();
        
        int listLength = 0;

        for (Map.Entry<Anime, Integer> entry : newMap.entrySet()) {
            
            // list of 20
            if (listLength <= 20 && (!entry.getKey().getName().contains(favString))) {

                suggestionsList.add(entry.getKey());
                listLength++;
            }
        }

        Collections.sort(suggestionsList);
        
        return suggestionsList;

    }

    public static void main(String[] args) throws NumberFormatException, IOException {

        List<Anime> listOfAnime = readAnimeCSV();

        Scanner in = new Scanner (System.in);
        System.out.println("Enter your favorite anime: ");

        String input = in.nextLine ();
        boolean contains = containsName(listOfAnime, input);
        
        // no repeats of favorite anime
        if (contains) {
            
            // get same index
            List<String> namesList = listOfAnime.stream()
                                                .map(Anime::getName)
                                                .collect(Collectors.toList());

            int index = namesList.indexOf(input);

            List<List<String>> listOfGenres = genresLists(listOfAnime);

            List<Anime> suggestionList = getSuggestions(listOfGenres.get(index), input);
            System.out.println("Suggestions: ");
            
            // display rating
            for (int i = (suggestionList.size() - 1); i >= 0; i--) {
                System.out.println(suggestionList.get(i) + "  Rating: " + suggestionList.get(i).getRating());
            }
        }

        in.close ();
    }
}
