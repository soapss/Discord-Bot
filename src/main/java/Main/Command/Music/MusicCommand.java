package Main.Command.Music;

import Main.Command.DefaultCommand;
import Main.Command.CommandCategory;
import net.dv8tion.jda.api.entities.*;

//template for music commands
public abstract class MusicCommand extends DefaultCommand {

    public MusicCommand() {
        super();
    }

    @Override
    public String getCommand() {
        return null;
    }

    @Override
    public String getDes() {
        return null;
    }

    //category music
    @Override
    public CommandCategory getCat() {
        return CommandCategory.MUSIC;
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {

    }

    //function to check if user from server is connected
    public static boolean isConnected(Guild guild, User user){
        return guild.getSelfMember().getVoiceState().getChannel() == null ||
                guild.getSelfMember().getVoiceState().getChannel() != guild.getMember(user).getVoiceState().getChannel();
    }

}
