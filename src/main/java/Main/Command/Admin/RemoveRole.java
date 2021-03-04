package Main.Command.Admin;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RemoveRole extends AdminCommand {

    public RemoveRole() {
        super();
    }

    @Override
    public String getCommand() {
        return "removerole";
    }

    @Override
    public String getDes() {
        return "[@user] [role]";
    }

    //category of super, which is admin
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "rr";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        if(!guild.getMember(user).hasPermission(Permission.MANAGE_ROLES)){
            tc.sendMessage("Invalid permissions.").queue();
            return;
        }
        //get mentioned user from message
        Member toRemoveRole = getMention(msg);
        if(toRemoveRole == null){
            tc.sendMessage("Mention a valid user.").queue();
            return;
        }

        Optional<String> s = Arrays.stream(msg.getContentRaw().split("\\s+")).skip(2).findFirst();
        if(!s.isPresent()) {
            tc.sendMessage("Enter a role.").queue();
            return;
        }
        List<Role> roles = guild.getRolesByName(s.get(), true);
        if (roles.isEmpty()) {
            tc.sendMessage("Name a valid role.").queue();
            return;
        }

        //remove role from member
        guild.removeRoleFromMember(toRemoveRole, roles.get(0)).queue();
        //react to command
        msg.addReaction(Emoji.OK.getEmoji()).queue();
    }
}
