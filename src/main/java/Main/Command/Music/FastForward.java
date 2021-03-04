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

public class FastForward extends MusicCommand{

    public FastForward() {
        super();
    }

    @Override
    public String getCommand() {
        return "fastforward";
    }

    @Override
    public String getDes() {
        return "[seconds]";
    }

    //category of super, which is music
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "ff";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //get player of server
        AudioPlayer player = MusicManager.getTrackScheduler(guild).getPlayer();
        //convert message without command to int
        //set currently playing song to position of current position minus the int value in milliseconds
        Optional<String> s = Arrays.stream(msg.getContentRaw().split("\\s+")).skip(1).findFirst();
        if(!s.isPresent()){
            tc.sendMessage(String.format("Usage: ~%s %s", getCommand(), getDes())).queue();
            return;
        }
        AudioTrack t = player.getPlayingTrack();
        try {
            t.setPosition(t.getPosition() - Integer.parseInt(s.get()) * -1000L);
        }catch (NumberFormatException e){
            tc.sendMessage("Enter a time in seconds.").queue();
            return;
        }
        //react ok to command
        msg.addReaction(Emoji.ARROW_FORWARD.getEmoji()).queue();
    }
}
