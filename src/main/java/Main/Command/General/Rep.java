package Main.Command.General;

import Main.Command.CommandCategory;
import Main.User.UserManager;
import Main.Util.Embed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

import java.time.LocalDateTime;

public class Rep extends GeneralCommand{
    public Rep() {
        super();
    }

    @Override
    public String getCommand() {
        return "rep";
    }

    @Override
    public String getDes() {
        return "[@user]";
    }

    //category of super which is general
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "~r";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //get mentioned user from messages
        Member mention = getMention(msg);
        if(mention == null){
            tc.sendMessage("Mention a valid user.").queue();
            return;
        }

        User u = mention.getUser();
        //if mentioned is user who sent message
        if(user.equals(u)){
            tc.sendMessage("Can't rep yourself!").queue();
            return;
        }
        //if mentioned is a bot
        if(user.isBot()){
            tc.sendMessage("Can't rep bots!").queue();
            return;
        }

        Main.User.User uObj = UserManager.getUser(u.getId());
        //if user is on cooldown for repping
        LocalDateTime t = uObj.getRepTime();
        if(t != null && t.plusDays(1).isAfter(LocalDateTime.now())) {
            tc.sendMessage(String.format("You can rep again in %s!", t.plusDays(1))).queue();
            return;
        }

        //add rep point to mentioned
        uObj.setRep(1);
        //set time repped to now
        uObj.setRepTime(LocalDateTime.now());
        //send message with mentioned rep points
        EmbedBuilder em = Embed.embed();
        em.setDescription(String.format("User %s has %d reputation points!", u.getAsMention(), uObj.getRep()));
        tc.sendMessage(em.build()).queue();
    }

}
