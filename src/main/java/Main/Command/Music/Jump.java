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

public class Jump extends MusicCommand{
    public Jump() {
        super();
    }

    @Override
    public String getCommand() {
        return "jump";
    }

    @Override
    public String getDes() {
        return "[index]";
    }

    //category of super, which is music
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "jp";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        Optional<String> s = Arrays.stream(msg.getContentRaw().split("\\s+")).skip(1).findFirst();
        if(!s.isPresent()) {
            tc.sendMessage("Enter an index.").queue();
            return;
        }
        try {
            //call jump function based on content of message converted to int, if the function returns false, send invalid index message
            if(!MusicManager.getTrackScheduler(guild).jump(Integer.parseInt(s.get())-1)) {
                tc.sendMessage("Invalid index.").queue();
                return;
            }
        }
        //if index does not exist
        catch (NullPointerException e){
            tc.sendMessage("Invalid index.").queue();
            return;
        }
        //if cannot be converted to int
        catch (NumberFormatException e){
            tc.sendMessage("Enter an index.").queue();
            return;
        }
        //react ok
        msg.addReaction(Emoji.OK.getEmoji()).queue();
    }
}
