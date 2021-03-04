package Main.Command.Admin;

import Main.Command.CommandCategory;
import net.dv8tion.jda.api.entities.*;

//this is a test command, intended to be deleted later, basically turns off the bot with command
public class Terminate extends AdminCommand{
    public Terminate() {
        super();
    }

    @Override
    public String getCommand() {
        return "end";
    }

    @Override
    public String getDes() {
        return "";
    }

    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "die";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //if the user is me
        if(!guild.getMember(user).getId().equals("225430463261442050")){
            tc.sendMessage("Invalid permissions.").queue();
            return;
        }
        System.exit(0);
    }
}
