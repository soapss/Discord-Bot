package Main.Command.Admin;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

public class Deafen extends AdminCommand {

    public Deafen() {
        super();
    }

    @Override
    public String getCommand() {
        return "deafen";
    }

    @Override
    public String getDes() {
        return "[@user]";
    }

    //category of super which is admin
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "d";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //check perm
        if(!guild.getMember(user).hasPermission(Permission.VOICE_DEAF_OTHERS)){
            tc.sendMessage("Invalid permissions.").queue();
            return;
        }

        //get mention user from message
        Member toDeafen = getMention(msg);
        if(toDeafen == null){
            tc.sendMessage("Mention a valid user.").queue();
            return;
        }

        //check if user is in vc
        if(!toDeafen.getVoiceState().inVoiceChannel()){
            tc.sendMessage("This user is not in a voice channel.").queue();
            return;
        }

        //deafen user
        guild.deafen(toDeafen, true).queue();
        msg.addReaction(Emoji.OK.getEmoji()).queue();
    }
}

