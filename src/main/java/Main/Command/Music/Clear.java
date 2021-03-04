package Main.Command.Music;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import Main.Music.MusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class Clear extends MusicCommand {

    public Clear() {
        super();
    }

    @Override
    public String getCommand() {
        return "clear";
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
        return "c";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //call clear function for player of server
        MusicManager.getTrackScheduler(guild).clear();
        //react ok to commmand
        msg.addReaction(Emoji.OK.getEmoji()).queue();
    }
}
