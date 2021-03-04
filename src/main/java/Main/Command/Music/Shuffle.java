package Main.Command.Music;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import Main.Music.MusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class Shuffle extends MusicCommand{
    public Shuffle() {
        super();
    }

    @Override
    public String getCommand() {
        return "shuffle";
    }

    @Override
    public String getDes() {
        return "";
    }

    //category of super class, which is music
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "sh";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //call player for server to shuffle
        MusicManager.getTrackScheduler(guild).shuffle();
        //react ok to command
        msg.addReaction(Emoji.OK.getEmoji()).queue();
    }
}
