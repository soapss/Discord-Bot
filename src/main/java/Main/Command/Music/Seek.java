package Main.Command.Music;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import Main.Music.MusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.Arrays;
import java.util.Optional;

public class Seek extends MusicCommand{
    public Seek() {
        super();
    }

    @Override
    public String getCommand() {
        return "seek";
    }

    @Override
    public String getDes() {
        return "[timestamp]";
    }

    //category of super, which is music
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "sk";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //call player for server to set duration to processed duration
        //processed duration takes in content of message without command
        Optional<String> s = Arrays.stream(msg.getContentRaw().split("\\s+")).skip(1).findFirst();
        if(!s.isPresent()){
            tc.sendMessage(String.format("Usage: ~%s %s", getCommand(), getDes())).queue();
            return;
        }
        MusicManager.getTrackScheduler(guild).getPlayer().getPlayingTrack().setPosition(processDuration(s.get()));
        //react ok to command
        msg.addReaction(Emoji.OK.getEmoji()).queue();
    }

    //convert time stamp to millisecond
    private long processDuration(String ts){
        //separate to arrays based on delimiter :
        String[] timeStamp = ts.trim().split(":");
        long ms = 0;
        //math stuff that somehow works
        for(int i = timeStamp.length-1; i > -1; i--)
            ms += Math.pow(60, timeStamp.length - 1 - i) * Integer.parseInt(timeStamp[i]);
        return ms * 1000;
    }
}
