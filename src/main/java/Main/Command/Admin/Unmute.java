package Main.Command.Admin;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

public class Unmute extends AdminCommand {

    public Unmute() {
        super();
    }

    @Override
    public String getCommand() {
        return "unmute";
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
        return "um";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        if(!guild.getMember(user).hasPermission(Permission.VOICE_MUTE_OTHERS)){
            tc.sendMessage("Invalid permissions.").queue();
            return;
        }
        //get mentioned user from message
        Member toUnmute = getMention(msg);
        if(toUnmute == null){
            tc.sendMessage("Mention a valid user.").queue();
            return;
        }

        if(!toUnmute.getVoiceState().inVoiceChannel()){
            tc.sendMessage("This user is not in a voice channel.").queue();
            return;
        }
        //unmute member
        guild.mute(toUnmute, false).queue();
        //react to command
        msg.addReaction(Emoji.CHECK.getEmoji()).queue();
    }
}
