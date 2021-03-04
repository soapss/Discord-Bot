package Main.Command.Music;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import Main.Music.MusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class Pause extends MusicCommand {

    public Pause() {
        super();
    }

    @Override
    public String getCommand() {
        return "pause";
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
        return "pp";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //set player of server to paused
        MusicManager.getTrackScheduler(guild).getPlayer().setPaused(true);
        //react to command
        msg.addReaction(Emoji.PAUSE.getEmoji()).queue();
    }
}
