package Main.Command.General;

import Main.Command.CommandCategory;
import Main.User.Econ;
import Main.User.UserManager;
import Main.Util.Embed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

import java.time.LocalDateTime;

public class Daily extends GeneralCommand{

    public Daily() {
        super();
    }

    @Override
    public String getCommand() {
        return "daily";
    }

    @Override
    public String getDes() {
        return "";
    }

    //category of super, which is general
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "d";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //get user id
        String uID = user.getId();

        Main.User.User uObj =  UserManager.getUser(uID);
        //if user is on cooldown
        LocalDateTime t = uObj.getDailyTime();
        if(t != null && t.plusDays(1).isAfter(LocalDateTime.now())) {
            tc.sendMessage(String.format("You can claim your next daily in %s!", getCD(t.plusDays(1)))).queue();
            return;
        }

        //get daily for user
        Econ.getDaily(uID);
        //set daily claim time to now
        uObj.setDailyTime(LocalDateTime.now());
        //send currency update message
        EmbedBuilder em = Embed.embed();
        em.setDescription(String.format("User %s has %d omos.", user.getAsMention(), uObj.getEcon()));
        tc.sendMessage(em.build()).queue();
    }
}
