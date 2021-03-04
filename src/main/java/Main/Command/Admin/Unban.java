package Main.Command.Admin;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.Arrays;
import java.util.Optional;

public class Unban extends AdminCommand {
    public Unban() {
        super();
    }

    @Override
    public String getCommand() {
        return "unban";
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
        return "ub";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        if(!guild.getMember(user).hasPermission(Permission.BAN_MEMBERS)){
            tc.sendMessage("Invalid permissions.").queue();
            return;
        }
        //get user id based on message
        Optional<String> s = Arrays.stream(msg.getContentRaw().split("\\s+")).skip(1).findFirst();
        if(!s.isPresent()) {
            tc.sendMessage("Mention a valid user.").queue();
            return;
        }
        String id = s.get();
        String unban = id.substring(3, id.length() - 1);
        //unban user based on id from above
        guild.unban(unban).queue();
        //react to command
        msg.addReaction(Emoji.OK.getEmoji()).queue();
    }
}
