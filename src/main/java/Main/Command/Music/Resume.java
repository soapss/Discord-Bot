package Main.Command.Music;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import Main.Music.MusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class Resume extends MusicCommand {

    public Resume() {
        super();
    }

    @Override
    public String getCommand() {
        return "resume";
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
        return "r";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //call player of server to unpause
        MusicManager.getTrackScheduler(guild).getPlayer().setPaused(false);
        //react to command
        msg.addReaction(Emoji.ARROW_FORWARD.getEmoji()).queue();
    }
}
