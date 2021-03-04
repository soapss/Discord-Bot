package Main.Music;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//this is the webscrapping form, i'm using this because youtube api only allows a set number of searches and i needed more for testing code
//web scrapping is really slow, should change it to youtube api for final version
public class Youtube {

//link formats like the spotify class stuff
    private static final String YOUTUBE_SEARCH_URL = "https://www.youtube.com/results?search_query=song";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
    private static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    private static final String THUMBNAIL_URL = "https://i.ytimg.com/vi/";
    //a map to store the search result
    private static HashMap<Integer, ArrayList<String>> result;


    //pass in search word to search on youtube
    public static String Search(String query){

        //call the youtubesearch function to get the youtube link
        String results = YoutubeSearch(query);

        try{
            //extract the identifier by substring the link
            int index = results.indexOf("/watch?v=");
            int index2 = results.indexOf("\"",index);
            return String.format("https://www.youtube.com%s",results.substring(index,index2));
        }
        catch(IndexOutOfBoundsException e){
            return null;
        }
    }

    //pattern finder and formatting stuff for searched list from youtubesearch function
    public static String SearchList(String query){
        String results = YoutubeSearch(query);
        result = new HashMap<>();

        Pattern pattern = Pattern.compile("\"title\":\\{\"runs\":\\[\\{\"text\":\"(.*?)\"}]");
        Matcher matcher = pattern.matcher(results);
        ArrayList<String> titles = new ArrayList<>();
        int counter=0;
        while(counter<9 && matcher.find()) {
            String title = matcher.group();
            title = title.substring(26,title.length()-3).replace("\\u0026","&");
            titles.add(title);
            counter++;
        }

        counter = 0;
        while(counter < 9 && results.length()!=0){
            int index = results.indexOf("/watch?v=");
            if(index<0) break;
            int index2 = results.indexOf("\"",index);

            ArrayList<String> meta = new ArrayList<>();
            String link = String.format("https://www.youtube.com%s",results.substring(index,index2));
            meta.add(titles.get(counter));
            meta.add(link);
            result.put(counter, meta);
            results = results.substring(index2);
            counter++;
        }

        return getSearch(titles);

    }

    //webscrapping for youtube searching query
    public static String YoutubeSearch(String query){
        String url = YOUTUBE_SEARCH_URL.replace("song",
                query.replaceAll("\\(.*?\\)","")
                        .replaceAll("\\[.*?\\]","")
                        .replace("&","%26")
                        .replace("#","%23")
                        .replace(" ","+"));
        try {
            Document doc = Jsoup.connect(url).userAgent(USER_AGENT).get();
            Elements scripts = doc.select("script");

            for (Element e : scripts) {
                if (e.data().contains("ytInitialData")) {
                    return e.data();
                }
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//getting search result for ~search
    public static String getSearch(List<String> titles){
        //counter
        AtomicInteger pos = new AtomicInteger(1);
        //builder to build the result to display
        StringBuilder builder = new StringBuilder();

        //for each title in list of titles
        for (String s : titles){
            //format into position) title
            builder.append(pos.getAndIncrement());
            builder.append(") ");
            builder.append(String.format("%-40s", s));
            builder.append("\n");
        }

        //return the entire list of titles combined
        return builder.toString();
    }

    //get the result at index
    public static String getSearch(int index){
        return result.get(index-1).get(1);
    }

    //get video url by adding identifier to default url link
    public static String getURL(String vidID){
        return YOUTUBE_URL.concat(vidID);
    }

    //can be used if you want thumbnail
    public static String getThumbnail(String vidID){
        return THUMBNAIL_URL.concat(vidID).concat("/0.jpg");
    }
}
