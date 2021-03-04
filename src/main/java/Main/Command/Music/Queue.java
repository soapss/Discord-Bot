package Main.Command.Music;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import Main.Music.MusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class Queue extends MusicCommand{

    public Queue() {
        super();
    }

    @Override
    public String getCommand() {
        return "queue";
    }

    @Override
    public String getDes() {
        return "";
    }

    //category of super, which is music
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "q";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //call player of server to print queue
        tc.sendMessage(MusicManager.getTrackScheduler(guild).printQ()).queue(m -> {
            if(m.getContentRaw().startsWith("```json")) {
                //add reactions for user navigation
                m.addReaction(Emoji.ARROW_UP.getEmoji()).queue();
                m.addReaction(Emoji.ARROW_DOWN.getEmoji()).queue();
            }
        });
    }
}
