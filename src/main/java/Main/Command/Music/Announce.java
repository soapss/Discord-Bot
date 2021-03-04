package Main.Command.Music;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import Main.Music.MusicManager;
import Main.Util.MessageProcessor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class Announce extends MusicCommand{

    public Announce() {
        super();
    }

    @Override
    public String getCommand() {
        return "announce";
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
        return "an";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //get status based on content of message without command
        boolean status = !MessageProcessor.excludeCom(msg).equalsIgnoreCase("off");
        //set status for player of server
        MusicManager.getTrackScheduler(guild).announce(status);
        //react ok to command
        msg.addReaction(Emoji.OK.getEmoji()).queue();
    }
}
