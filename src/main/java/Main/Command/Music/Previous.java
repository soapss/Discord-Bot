package Main.Command.Music;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import Main.Music.MusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class Previous extends MusicCommand{
    public Previous() {
        super();
    }

    @Override
    public String getCommand() {
        return "previous";
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
        return "pv";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //call player of server to execute previous function
        MusicManager.getTrackScheduler(guild).previous();
        //react to command
        msg.addReaction(Emoji.REWIND.getEmoji()).queue();
    }
}
