package Main.Command.Admin;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

public class Undeafen extends AdminCommand {

    public Undeafen() {
        super();
    }

    @Override
    public String getCommand() {
        return "undeafen";
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
        return "ud";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        if(!guild.getMember(user).hasPermission(Permission.VOICE_DEAF_OTHERS)){
            tc.sendMessage("Invalid permissions.").queue();
            return;
        }
        //get mentioned user from message
        Member toUndeafen = getMention(msg);
        if(toUndeafen == null){
            tc.sendMessage("Mention a valid user.").queue();
            return;
        }

        if(!toUndeafen.getVoiceState().inVoiceChannel()){
            tc.sendMessage("This user is not in a voice channel.").queue();
            return;
        }
        //undeafen user
        guild.deafen(toUndeafen, false).queue();
        //react to command
        msg.addReaction(Emoji.CHECK.getEmoji()).queue();
    }
}
