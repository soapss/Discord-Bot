package Main.Command;

import net.dv8tion.jda.api.entities.*;

//default template for all commands
public abstract class DefaultCommand {

    //object
    public DefaultCommand(){}

    //get command name
    public abstract String getCommand();

    //get description
    public abstract String getDes();

    //get command category
    public abstract CommandCategory getCat();

    //get command alias
    public abstract String getAlias();

    //execution function for command
    public abstract void exe(Guild guild, User user, TextChannel tc, Message msg);

}
