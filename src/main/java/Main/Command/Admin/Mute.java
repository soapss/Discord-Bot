package Main.Command.Admin;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

public class Mute extends AdminCommand {

    public Mute() {
        super();
    }

    @Override
    public String getCommand() {
        return "mute";
    }

    @Override
    public String getDes() {
        return "[@user]";
    }

    //category of super, which is admin
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "m";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //check perms
        if(!guild.getMember(user).hasPermission(Permission.VOICE_MUTE_OTHERS)){
            tc.sendMessage("Invalid permissions.").queue();
            return;
        }

        //get mentioned member in message
        Member toMute = getMention(msg);
        if(toMute == null){
            tc.sendMessage("Mention a valid user.").queue();
            return;
        }

        if(!toMute.getVoiceState().inVoiceChannel()){
            tc.sendMessage("This user is not in a voice channel.").queue();
            return;
        }
        //mute member
        guild.mute(toMute, true).queue();
        //react to command
        msg.addReaction(Emoji.NO_MOUTH.getEmoji()).queue();
    }
}
