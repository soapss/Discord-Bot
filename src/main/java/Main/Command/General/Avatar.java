package Main.Command.General;

import Main.Command.CommandCategory;
import Main.Util.Embed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

public class Avatar extends GeneralCommand {
    public Avatar() {
        super();
    }

    @Override
    public String getCommand() {
        return "avatar";
    }

    @Override
    public String getDes() {
        return "(@user)";
    }

    //category of super, which is general
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "av";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //create embed
        EmbedBuilder em = Embed.embed();
        //get mentioned user, if it is null, then set it to user that sent the message
        User u = getMention(msg) == null ? user : getMention(msg).getUser();
        //add user's tag and avatar
        em.setTitle(u.getAsTag()+"'s avatar").setImage(u.getAvatarUrl());
        //send embed
        tc.sendMessage(em.build()).queue();
    }
}

