package Main.Command.Music;

import Main.Command.CommandCategory;
import Main.Music.MusicManager;
import Main.Util.Embed;
import Main.Util.MessageProcessor;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

public class Lyric extends MusicCommand{

    //default search url stuff
    private final String GOOGLE_SEARCH_URL = "https://www.google.com/search?=&q=query+site:genius.com";
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";


    public Lyric() {
        super();
    }

    @Override
    public String getCommand() {
        return "lyric";
    }

    @Override
    public String getDes() {
        return "[artist] [song]";
    }

    //category of super, which is music
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "ly";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {

        //content of message without command
        String message = MessageProcessor.excludeCom(msg);
        //if the message is empty, search lyric of current playing song, else search lyric of message
        String search = message.equals("") ? MusicManager.getTrackScheduler(guild).getPlayer().getPlayingTrack().getInfo().title : message;

        //url formatting
        String url = GOOGLE_SEARCH_URL.replace("query",
                search.replaceAll("\\(.*?\\)","")
                        .replaceAll("\\[.*?\\]","")
                        .replace("&","%26")
                        .replace("#","%23")
                        .replace(" ","+"));

        //webscrapping
        try {
            Document doc = Jsoup.connect(url).userAgent(USER_AGENT).get();

            ArrayList<String> links = new ArrayList<>();
            StringJoiner titles = new StringJoiner("\n");
            int i = 1;
            for(Element e : doc.select("a[href^=https://genius.com][href*=lyrics]")){
                if(i>10) break;
                links.add(e.attr("href"));
                String title = e.text();
                titles.add(String.format("%d) %s",i,title.substring(0,title.indexOf("genius.com"))));
                i++;
            }

            //send results
            EmbedBuilder em = Embed.embed();
            em.setTitle(String.format("Search Results | %s",search));
            if(links.size()==0){
                em.setDescription("No results found!");
                tc.sendMessage(em.build()).queue();
                return;
            }else{
                em.setDescription("Reply with index of lyrics to be searched within 30 seconds.");
            }

            em.addField("", titles.toString(),true);
            tc.sendMessage(em.build()).queue();

            EventWaiter waiter = new EventWaiter();
            guild.getJDA().addEventListener(waiter);
            waiter.waitForEvent(GuildMessageReceivedEvent.class,
                    (event) -> event.getAuthor().equals(user) && event.getMessage().getContentRaw().matches(String.format("[1-%d]",links.size())),
                    (event) -> {
                        //Prune.exe(tc,2);
                        printLyrics(tc,links.get(Integer.parseInt(event.getMessage().getContentRaw())-1));
                    },
                    30, TimeUnit.SECONDS,
                    null);

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //printing the searched lyrics
    public void printLyrics(TextChannel tc, String url){
        try {
            String lyrics = Search(url);

            //formatting
            while (lyrics.length() != 0) {
                if (lyrics.length() >= 1987) {
                    String sub = lyrics.substring(0, 1987);
                    if (sub.lastIndexOf("]") < sub.lastIndexOf("[")) {
                        tc.sendMessage(String.format("```ini\n%s\n```", lyrics.substring(0, sub.lastIndexOf("[")))).queue();
                        lyrics = " " + lyrics.substring(sub.lastIndexOf("["));
                    } else {
                        tc.sendMessage(String.format("```ini\n%s\n```", sub)).queue();
                        lyrics = lyrics.substring(1987);
                    }
                } else {
                    tc.sendMessage(String.format("```ini\n%s\n```", lyrics)).queue();
                    lyrics = "";
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //searching the lyrics of specific url
    public static String Search(String url) throws IOException {

        //webscrape lyrics
        Document doc = Jsoup.connect(url).userAgent("Mozilla").get();

        String lyrics = Jsoup.clean(doc.select("div.lyrics").select("p").first().html(), Whitelist.basic().removeTags("a"));
        lyrics = lyrics.replaceAll("\\<.*?\\>","").replaceAll("&amp; \n","& ");

        //return lyrics
        return String.format("\n%s\n\n %s", doc.title(), lyrics);
    }

}
