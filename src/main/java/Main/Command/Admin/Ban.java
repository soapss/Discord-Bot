package Main.Command.Admin;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

public class Ban extends AdminCommand {
    private final int DEFAULT_DEL_DAY = 0;

    public Ban() {
        super();
    }

    @Override
    public String getCommand() {
        return "ban";
    }

    @Override
    public String getDes() {
        return "[@user] (days) (reason)";
    }

    //category of super, which is admin
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "b";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //check for permission to ban
        if(!guild.getMember(user).hasPermission(Permission.BAN_MEMBERS)){
            tc.sendMessage("Invalid permissions.").queue();
            return;
        }
        //get mentioned member
        Member toBan = getMention(msg);
        if(toBan == null){
            tc.sendMessage("Mention a valid user.").queue();
            return;
        }
        //ban member
        guild.ban(toBan, DEFAULT_DEL_DAY);
        msg.addReaction(Emoji.BAN.getEmoji()).queue();
        tc.sendMessage(String.format("User %s has been banned.", toBan.getAsMention())).queue();
    }
}
