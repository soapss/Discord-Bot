package Main.Handler.Reaction;

import Main.Handler.ReactionType;
import Main.Misc.EmojiCategory;
import Main.Music.MusicManager;
import Main.Music.Youtube;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

//currently only for ~search in music command
public class GuildSelection implements ReactionType {
    //get category of reaction
    @Override
    public EmojiCategory getType() {
        return EmojiCategory.SELECTION;
    }

    //exe function
    @Override
    public void exe(MessageReactionAddEvent event) {
        //convert number choice emoji to int
        int choice = Integer.parseInt(event.getReactionEmote().getEmoji().substring(0, 1));
        //load the item of search index choice for the server music player
        MusicManager.manager.loadItem(Youtube.getSearch(choice), MusicManager.getTrackScheduler(event.getGuild()));
        //remove the user reaction from the message
        event.getTextChannel().removeReactionById(event.getMessageId(), event.getReactionEmote().getEmoji(), event.getUser()).queue();
    }
}
