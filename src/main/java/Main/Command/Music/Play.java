package Main.Command.Music;

import Main.Command.CommandCategory;
import Main.Handler.CommandHandler;
import Main.Misc.Emoji;
import Main.Music.MusicManager;
import Main.Music.SpotifySearch;
import Main.Music.Youtube;
import Main.Util.MessageProcessor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.LinkedList;
import java.util.List;

public class Play extends MusicCommand {

    private static List<String> queries;

    public Play() {
        super();
    }

    @Override
    public String getCommand() {
        return "play";
    }

    @Override
    public String getDes() {
        return "[song/URL]";
    }

    //category of super, which is music
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "p";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {

        //check if user is connected to vc
        if(isConnected(guild, user)) {
            //self join vc
            CommandHandler.exe("join", guild, user, tc, msg);
        }

        //make new list to store track queries
        queries = new LinkedList<>();
        //process query
        queryProcessor(guild, MessageProcessor.excludeCom(msg));

        //react to command
        msg.addReaction(Emoji.OK.getEmoji()).queue();
    }

    private boolean queryProcessor(Guild guild, String query){
        //if the query is not an url, call youtube to search the query and load the result to player for server
        if(!isURL(query)) MusicManager.manager.loadItem(Youtube.Search(query), MusicManager.getTrackScheduler(guild));
        //if the query is a youtube link or soundcloud link, load the link into the player for server
        else if(isYoutube(query) || isSoundCloud(query))
            MusicManager.manager.loadItem(query, MusicManager.getTrackScheduler(guild));
        //if the query is a spotify link
        else if(isSpotify(query))
            //call spotify api and youtube search the result for each song and load them into the player for server
            SpotifySearch.Search(query).forEach(t -> MusicManager.manager.loadItem(Youtube.Search(t), MusicManager.getTrackScheduler(guild)));
        else return false;
        return true;
    }

    //stupid hard coded checks lol
    private boolean isURL(String url){
        return url.startsWith("http");
    }

    private boolean isYoutube(String url){
        return url.startsWith("https://www.youtube.com/watch?v=");
    }

    private boolean isSoundCloud(String url){
        return url.startsWith("https://soundcloud.com/");
    }

    private static boolean isSpotify(String url){
        return url.startsWith("https://open.spotify.com/");
    }

}
