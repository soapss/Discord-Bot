package Main.Handler.Reaction;

import Main.Command.DefaultCommand;
import Main.Command.PageMap;
import Main.Handler.PageManager;
import Main.Handler.ReactionType;
import Main.Misc.Emoji;
import Main.Misc.EmojiCategory;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

import java.util.Arrays;

//made for reactions to ~help message
public class PrivatePage implements ReactionType {
    //get category of reaction
    @Override
    public EmojiCategory getType() {
        return EmojiCategory.PRIVATE;
    }

    //execution function
    @Override
    public void exe(MessageReactionAddEvent event) {
        //get emoji
        Emoji emo = Arrays.stream(Emoji.values()).filter(e -> e.getEmoji().equals(event.getReactionEmote().getEmoji())).findFirst().get();
        //get message channel aka dm
        MessageChannel mc = event.getChannel();
        //get the command of the message based on stored messageid
        DefaultCommand com = PageMap.hasCommand(event.getMessageId());
        //content to print
        MessageEmbed content = null;
        //if reaction is left arrow, print previous page for command com
        if(emo.equals(Emoji.ARROW_LEFT)) {
            content = PageManager.getPrinter(mc).getPreviousPage(com);
            //if reaction is right arrow, print next page for command com
        } else if(emo.equals(Emoji.ARROW_RIGHT)) {
            content = PageManager.getPrinter(mc).getNextPage(com);
        }

        //if content is empty, return
        if(content == null) return;

        //send another message because bot cant remove user reactions in dm
        mc.sendMessage(content).queue(m -> {
            //react to new message to allow user to choose
            m.addReaction(Emoji.ARROW_LEFT.getEmoji()).queue();
            m.addReaction(Emoji.ARROW_RIGHT.getEmoji()).queue();
            //set the new message's id to the command in pagemap for future reactions to this message
            PageMap.setCommand(m.getId(), com);
        });
        //delete previous message
        mc.deleteMessageById(event.getMessageId()).queue();
    }
}
