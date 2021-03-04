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

public class Volume extends MusicCommand{

    public Volume() {
        super();
    }

    @Override
    public String getCommand() {
        return "vol";
    }

    @Override
    public String getDes() {
        return "[volume]";
    }

    //category of super, which is music
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "v";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        Optional<String> s = Arrays.stream(msg.getContentRaw().split("\\s+")).skip(1).findFirst();
        if(!s.isPresent()){
            tc.sendMessage(String.format("Usage: ~%s %s", getCommand(), getDes())).queue();
            return;
        }
        int vol;
        try {
            //make the message excluding the command into numbers
            vol = Integer.parseInt(s.get());
            //set volume to that value above
        }
        //if cannot be converted to number
        catch (NumberFormatException e){
            tc.sendMessage("Enter a value 0-100.").queue();
            return;
        }
        MusicManager.getTrackScheduler(guild).getPlayer().setVolume(vol);
        //react ok to the command
        msg.addReaction(Emoji.OK.getEmoji()).queue();
    }
}
