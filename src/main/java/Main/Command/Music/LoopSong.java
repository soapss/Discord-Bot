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

public class LoopSong extends MusicCommand{

    public LoopSong() {
        super();
    }

    @Override
    public String getCommand() {
        return "loopsong";
    }

    @Override
    public String getDes() {
        return "(off)";
    }

    //category of super, which is music
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "ls";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //get loop status based on message
        Optional<String> s = Arrays.stream(msg.getContentRaw().split("\\s+")).skip(1).findFirst();
        if(!s.isPresent()) {
            tc.sendMessage(String.format("Usage: ~%s %s", getCommand(), getDes())).queue();
            return;
        }
        boolean loop = !s.get().equalsIgnoreCase("off");
        //set loop status for player of server
        MusicManager.getTrackScheduler(guild).loopSong(loop);

        //react accordingly to command
        if(loop) msg.addReaction(Emoji.REPEAT.getEmoji()).queue();
        else msg.addReaction(Emoji.DISABLE.getEmoji()).queue();
    }
}
