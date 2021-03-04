package Main.Command.General;

import Main.Command.CommandCategory;
import Main.Command.DefaultCommand;
import net.dv8tion.jda.api.entities.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
//template for general command
public abstract class GeneralCommand extends DefaultCommand {
    public GeneralCommand() {
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

    //category of general
    @Override
    public CommandCategory getCat() {
        return CommandCategory.GENERAL;
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {

    }

    //function to get mentioned user in message
    public Member getMention(Message msg){
        if(msg.getMentionedMembers().isEmpty()) {
            return null;
        }
        else{
            return msg.getMentionedMembers().get(0);
        }
    }

    //get time left for user for next daily
    public String getCD(LocalDateTime t){
        Duration cd = Duration.between(LocalDateTime.now(), t);
        long ms = cd.toMillis();

        //convert time to hh:mm:ss or mm:ss
        //if less than an hr
        if(ms < 3600000)
            return String.format("%02d min %02d sec", TimeUnit.MILLISECONDS.toMinutes(ms),
                    TimeUnit.MILLISECONDS.toSeconds(ms) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms)));
        //if more than an hr
        else
            return String.format("%02d hr %02d min %02d sec", TimeUnit.MILLISECONDS.toHours(ms),
                    TimeUnit.MILLISECONDS.toMinutes(ms) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(ms)),
                    TimeUnit.MILLISECONDS.toSeconds(ms) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms)));
    }
}
