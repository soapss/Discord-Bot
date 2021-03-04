package Main.Command.Music;

import Main.Command.CommandCategory;
import Main.Music.MusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.Arrays;
import java.util.Optional;

public class Remove extends MusicCommand {

    public Remove() {
        super();
    }

    @Override
    public String getCommand() {
        return "remove";
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
        return "rm";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        Optional<String> s = Arrays.stream(msg.getContentRaw().split("\\s+")).skip(1).findFirst();
        if(!s.isPresent()) {
            tc.sendMessage("Enter an index.").queue();
            return;
        }
        try{
            //make the content of the message without the command an integer value
            //call the player to remove the track at that index
            tc.sendMessage(MusicManager.getTrackScheduler(guild).remove(Integer.parseInt(s.get()))).queue();
        }
        //if the input is not a valid number in existing range
        catch (NullPointerException e){
            tc.sendMessage("Invalid index.").queue();
            return;
        }
        //if the input is not a number
        catch (NumberFormatException e){
            tc.sendMessage("Enter an index.").queue();
            return;
        }
    }
}
