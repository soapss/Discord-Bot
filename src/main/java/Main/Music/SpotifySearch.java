package Main.Music;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import com.wrapper.spotify.requests.data.tracks.GetTrackRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

//spotify api class
public class SpotifySearch {
    //api object
    private static SpotifyApi spotify;
    //api keys
    private static final String CLIENT_ID = "insert key here";
    private static final String CLIENT_SECRET = "insert key here";
    //spotify link formats
    private static final String TRACK_URL = "https://open.spotify.com/track/";
    private static final String PLAYLIST_URL = "https://open.spotify.com/playlist/";
    //string regex to split by
    private static final String SPLIT_REGEX = "\\?si=";

    //search function for query, which is the spotify link, and returns a list of song identifiers
    public static List<String> Search(String query) {
        //list to store song identifiers
        List<String> queries = new LinkedList<>();

        try {
            String identifier;
            //if the query is a single spotify track
            if (isTrack(query)) {
                //remove the leading url and get the identifier
                identifier = query.replace(TRACK_URL,"").split(SPLIT_REGEX)[0];
                //call gettrack function to get the final identifier
                queries.add(getTrack(identifier));
            }
            //if the query is a spotify playlist
            if (isPlaylist(query)) {
                //remove the leading url and get the identifier
                identifier = query.replace(PLAYLIST_URL,"").split(SPLIT_REGEX)[0];
                //call getplaylist function to get the list of final identifiers
                queries = getPlaylist(identifier);
            }

        } catch(ParseException | SpotifyWebApiException | IOException e){
            e.printStackTrace();
        }

        //output the list
        return queries;
    }

    //check if url is that of a spotify track
    private static boolean isTrack(String query){
        return query.startsWith(TRACK_URL);
    }

    //check if url is that of a spotify playlist
    private static boolean isPlaylist(String query){
        return query.startsWith(PLAYLIST_URL);
    }

    //pass in identifier to get the track name and artist, use the results to do a youtube search and return youtube identifier
    private static String getTrack(String id) throws ParseException, SpotifyWebApiException, IOException {
        //api object
        GetTrackRequest trackRequest = spotify.getTrack(id).build();

        //execute request
        Track track = trackRequest.execute();
        //get info
        String song = track.getName();
        String artist = track.getArtists()[0].getName();

        //do a youtube search and return the youtube identifier
        return Youtube.Search(String.format("%s %s", song, artist));
    }

    //same thing as get track but for a list of songs
    private static List<String> getPlaylist(String id) throws ParseException, SpotifyWebApiException, IOException {
        //api object
        GetPlaylistsItemsRequest playlistRequest = spotify.getPlaylistsItems(id).build();

        //execute request for playlist
        Paging<PlaylistTrack> playlist = playlistRequest.execute();
        //list to store identifiers
        List<String> queries = new LinkedList<>();

        //for each track in playlist
        for(PlaylistTrack t : playlist.getItems()){
            //add to the list in the form of name artist
            queries.add(String.format("%s %s",((Track) t.getTrack()).getName(), Arrays.stream(((Track) t.getTrack()).getArtists()).findFirst().get().getName()));
        }

        //return list
        return queries;
    }

    //initializing spotify api only called when bot starts
    public static void init(){
        //create new api instance
        spotify = new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .build();

        ClientCredentialsRequest credReq = spotify.clientCredentials().build();
        try {
            ClientCredentials cred = credReq.execute();
            spotify.setAccessToken(cred.getAccessToken());

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            e.printStackTrace();
        }
    }
}
