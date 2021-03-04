package Main.Command.Admin;

import Main.Command.CommandCategory;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Prune extends AdminCommand {
    public Prune() {
        super();
    }

    @Override
    public String getCommand() {
        return "prune";
    }

    @Override
    public String getDes() {
        return "[amount]";
    }

    //category of super, which is admin
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "pr";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        if(!guild.getMember(user).hasPermission(Permission.MESSAGE_MANAGE)){
            tc.sendMessage("Invalid permissions.").queue();
            return;
        }

        //convert message without command to int
        Optional<String> s = Arrays.stream(msg.getContentRaw().split("\\s+")).skip(1).findFirst();
        if(!s.isPresent()) {
            tc.sendMessage(String.format("Usage: ~%s %s", getCommand(), getDes())).queue();
            return;
        }

        int num;
        try{
            num = Integer.parseInt(s.get());
        }
        catch (NumberFormatException e){
            tc.sendMessage("Invalid number of messages.").queue();
            return;
        }

        List<Message> msgs = tc.getHistory().retrievePast(num).complete();
        tc.deleteMessages(msgs).queue();
    }
}
