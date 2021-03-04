package Main.Handler;

import Main.Command.DefaultCommand;
import Main.Misc.Emoji;
import Main.Misc.EmojiCategory;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

//similar idea to trackscheduler but for printing, this is the most recent thing i worked on so its not good/complete
//don't need to do this complex, i tried to combine code for printing command messages, help, queue, and other stuff
//only ~queue, ~search, ~help currently work with this
public class Printer {

    //map a map of pages to a command
    private final HashMap<DefaultCommand, HashMap<Integer, MessageEmbed>> pageOf;
    //map the current page number to a command
    private final HashMap<DefaultCommand, AtomicInteger> pageNum;

    //object creation
    Printer(){
        pageOf = new HashMap<>();
        pageNum = new HashMap<>();
    }

    //initializes the maps when a new command is issued that needs to print pages
    public void newPageOf(DefaultCommand command, HashMap<Integer, MessageEmbed> pages){
        //map the pages to the command
        pageOf.put(command, pages);
        //set the current page number for the command to one and map it to the command
        pageNum.put(command, new AtomicInteger(1));
    }

    //get next page
    public MessageEmbed getNextPage(DefaultCommand command){
        //get content for next page
        MessageEmbed data = pageOf.get(command).get(pageNum.get(command).incrementAndGet());
        //if no content, undo page number change
        if(data == null) pageNum.get(command).decrementAndGet();
        //return content
        return data;
    }

    //get previous page
    public MessageEmbed getPreviousPage(DefaultCommand command){
        //get content for previous page
        MessageEmbed data = pageOf.get(command).get(pageNum.get(command).decrementAndGet());
        //if no content, undo page number change
        if(data== null) pageNum.get(command).incrementAndGet();
        //return content
        return data;
    }

    //for updating pages when reaction is registered
    public void update(MessageReactionAddEvent event){
        //get the emoji category type
        EmojiCategory type = Arrays.stream(Emoji.values()).filter(e -> e.getEmoji().equals(event.getReactionEmote().getEmoji())).findFirst().get().getCategory();
        //reflection stuff to find all the classes that implements the reactiontype interfacee
        Reflections reflections = new Reflections("Main.Handler.Reaction");
        Set<Class<? extends ReactionType>> reaction = reflections.getSubTypesOf(ReactionType.class);

        //for each class that implements reaction type, if the category value of class equals to category value of emoji, call the exe function of the class
        for(Class<? extends ReactionType> r : reaction) {
            try {
                if(Modifier.isAbstract(r.getModifiers())) continue;
                ReactionType category = r.getConstructor().newInstance();
                if(category.getType().equals(type)) category.exe(event);
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}
