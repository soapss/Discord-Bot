package Main.Command.Admin;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

public class Kick extends AdminCommand {

    public Kick() {
        super();
    }

    @Override
    public String getCommand() {
        return "kick";
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
        return "k";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //check perms
        if(!guild.getMember(user).hasPermission(Permission.KICK_MEMBERS)){
            tc.sendMessage("Invalid permissions.").queue();
            return;
        }
        //get mentioned user in message
        Member toKick = getMention(msg);
        if(toKick == null){
            tc.sendMessage("Mention a valid user.").queue();
            return;
        }
        //get reason from message
        guild.kick(toKick).queue();
        //react to command
        msg.addReaction(Emoji.OK.getEmoji()).queue();
        //send message
        tc.sendMessage(String.format("User %s has been kicked.", toKick.getAsMention())).queue();
    }
}
