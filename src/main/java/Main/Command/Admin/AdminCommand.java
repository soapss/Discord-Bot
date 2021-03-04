package Main.Command.Admin;

import Main.Command.CommandCategory;
import Main.Command.DefaultCommand;
import net.dv8tion.jda.api.entities.*;

//templates for admin commands
public abstract class AdminCommand extends DefaultCommand{
    public AdminCommand() {
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

    //category of admin
    @Override
    public CommandCategory getCat() {
        return CommandCategory.ADMIN;
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //check permission of user
        if(!guild.getMember(user).hasPermission(getCat().getPerm())){
            tc.sendMessage("Invalid permissions.").queue();
            return;
        }
    }

    //get the first mention in the message if any
    public Member getMention(Message msg){
        if(msg.getMentionedMembers().isEmpty()) {
            return null;
        }
        else{
            return msg.getMentionedMembers().get(0);
        }
    }

}
