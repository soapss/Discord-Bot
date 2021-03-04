package Main.Command.General;

import Main.Command.CommandCategory;
import Main.User.UserManager;
import Main.Util.Embed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

import java.time.format.DateTimeFormatter;

public class Profile extends GeneralCommand{
    public Profile() {
        super();
    }

    @Override
    public String getCommand() {
        return "profile";
    }

    @Override
    public String getDes() {
        return "[@user]";
    }

    //category of super, which is general
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "pf";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //get mentioned user, if no user, then set user to user who sent the message
        User u = getMention(msg) == null ? user : getMention(msg).getUser();
        //get user id of user above
        String uID = u.getId();
        Main.User.User uObj = UserManager.getUser(uID);
        //print user information
        EmbedBuilder em = Embed.embed();
        //add user name with avatar
        em.setAuthor(String.format("%s's Profile",u.getAsTag()),null,u.getEffectiveAvatarUrl());
        //formatting for date joined
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, yyyy MMMM dd HH:mm:ss a");
        //add date joined
        em.addField("Date Joined",u.getTimeCreated().format(formatter),true);
        //add currency
        em.addField("OMOs",String.valueOf(uObj.getEcon()),true);
        //add exp
        em.addField("EXP",String.valueOf(uObj.getExp()),true);
        //add rep points
        em.addField("Rep",String.valueOf(uObj.getRep()),true);

        //send the embed
        tc.sendMessage(em.build()).queue();
    }
}
