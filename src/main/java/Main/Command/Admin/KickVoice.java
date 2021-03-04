package Main.Command.Admin;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

public class KickVoice extends AdminCommand {

    public KickVoice() {
        super();
    }

    @Override
    public String getCommand() {
        return "kickvoice";
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
        return "kv";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //check perms
        if(!guild.getMember(user).hasPermission(Permission.VOICE_MOVE_OTHERS)){
            tc.sendMessage("Invalid permissions.").queue();
            return;
        }
        //get mentioned member from message
        Member toKick = getMention(msg);
        if(toKick == null){
            tc.sendMessage("Mention a valid user.").queue();
            return;
        }

        if(!toKick.getVoiceState().inVoiceChannel()){
            tc.sendMessage("This user is not in a voice channel.").queue();
            return;
        }
        //kick member from voice
        guild.kickVoiceMember(toKick).queue();
        //react to command
        msg.addReaction(Emoji.OK.getEmoji()).queue();
    }
}
