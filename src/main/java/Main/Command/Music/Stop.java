package Main.Command.Music;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import Main.Music.MusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class Stop extends MusicCommand {

    public Stop() {
        super();
    }

    @Override
    public String getCommand() {
        return "stop";
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
        return "s";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //call player for server to stop
        MusicManager.getTrackScheduler(guild).getPlayer().stopTrack();
        //react ok to command
        msg.addReaction(Emoji.STOP.getEmoji()).queue();
    }
}
