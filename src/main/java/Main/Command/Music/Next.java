package Main.Command.Music;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import Main.Music.MusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class Next extends MusicCommand {

    public Next() {
        super();
    }

    @Override
    public String getCommand() {
        return "next";
    }

    @Override
    public String getDes() {
        return "";
    }

    //category of super, which is music
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "n";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //call the skip function for player of server, if it returns false, send end of queue message
        if(!MusicManager.getTrackScheduler(guild).skip()){
            tc.sendMessage("End of queue reached.").queue();
            return;
        }
        //react ok to command
        msg.addReaction(Emoji.NEXT.getEmoji()).queue();
    }
}
