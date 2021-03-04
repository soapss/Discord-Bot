package Main.Handler.Reaction;

import Main.Handler.ReactionType;
import Main.Misc.Emoji;
import Main.Misc.EmojiCategory;
import Main.Music.MusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

import java.util.Arrays;

//initially planned to use this for ~queue, and league command page stuff and other search commands, but only ~queue is working atm
public class GuildPage implements ReactionType {
    //get reaction category
    @Override
    public EmojiCategory getType() {
        return EmojiCategory.PAGE;
    }

    //exe function
    @Override
    public void exe(MessageReactionAddEvent event) {
        //get emoji from reaction
        Emoji emo = Arrays.stream(Emoji.values()).filter(e -> e.getEmoji().equals(event.getReactionEmote().getEmoji())).findFirst().get();
        //get server
        Guild guild = event.getGuild();
        //get message id for reacted message
        String msgID = event.getMessageId();
        //variable to store updated content
        String content = null;
        //if reaction was down arrow, get next page of queue
        if(emo.equals(Emoji.ARROW_DOWN)) {
            content = MusicManager.getTrackScheduler(guild).getNextPage();
            //if reaction was up arrow, get previous page of queue
        } else if(emo.equals(Emoji.ARROW_UP)) {
            content = MusicManager.getTrackScheduler(guild).getPrevPage();
        }

        //remove user reaction
        event.getTextChannel().removeReactionById(event.getMessageId(), event.getReactionEmote().getEmoji(), event.getUser()).queue();
        //if content is empty, return
        if(content == null) return;

        //edit existing message with updated content
        event.getTextChannel().editMessageById(msgID, content).queue();
    }
}
