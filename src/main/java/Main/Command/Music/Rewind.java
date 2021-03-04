package Main.Command.Music;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import Main.Music.MusicManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.Arrays;
import java.util.Optional;

public class Rewind extends MusicCommand{
    public Rewind() {
        super();
    }

    @Override
    public String getCommand() {
        return "rewind";
    }

    @Override
    public String getDes() {
        return "[seconds]";
    }

    //category of supeer, which is music
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "rw";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        Optional<String> s = Arrays.stream(msg.getContentRaw().split("\\s+")).skip(1).findFirst();
        if(!s.isPresent()) {
            tc.sendMessage(String.format("Usage: ~%s %s", getCommand(), getDes())).queue();
            return;
        }
        //get player of server
        AudioPlayer player = MusicManager.getTrackScheduler(guild).getPlayer();
        AudioTrack t = player.getPlayingTrack();
        //get playing track and set the position to the current position minus the rewind value
        try {
            t.setPosition(t.getPosition() - Integer.parseInt(s.get()) * 1000L);
        } catch (NumberFormatException e){
            tc.sendMessage("Enter a time in seconds.").queue();
            return;
        }
        //react ok to command
        msg.addReaction(Emoji.ARROW_BACKWARD.getEmoji()).queue();
    }
}
