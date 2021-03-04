package Main.Command.Music;

import Main.Command.CommandCategory;
import Main.Handler.CommandHandler;
import Main.Misc.Emoji;
import Main.Music.Youtube;
import Main.Util.MessageProcessor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.Arrays;

import static Main.Misc.Emoji.*;

public class Search extends MusicCommand{
    public Search() {
        super();
    }

    @Override
    public String getCommand() {
        return "search";
    }

    @Override
    public String getDes() {
        return "[song]";
    }

    //category of super, which is music
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "find";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //if user is connected to vc, self join the vc
        if(isConnected(guild, user)) CommandHandler.exe("join", guild, user, tc, msg);

        //send message including result from a youtube search list based on content of message excluding command
        tc.sendMessage(String.format("```json\n%s```", Youtube.SearchList(MessageProcessor.excludeCom(msg)))).queue(m -> {
            //add number choice reactions
            for (Emoji emoji : Arrays.asList(ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE))
                m.addReaction(emoji.getEmoji()).queue();
        });
    }
}
