package Main.Handler;

import Main.Misc.EmojiCategory;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

//template for different reaction types, used for how to handle different types of reactions received by the bot
public interface ReactionType {

    //template functions
    EmojiCategory getType();

    void exe(MessageReactionAddEvent event);
}
