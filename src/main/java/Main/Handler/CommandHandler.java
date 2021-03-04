package Main.Handler;

import Main.Command.DefaultCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class CommandHandler {

    //set prefix for bot
    private static final String PREFIX = "~";

    //map for command name to command object
    private static final HashMap<String, DefaultCommand> commands = new HashMap<>();
    //map for command alias to command object
    private static final HashMap<String, DefaultCommand> aliases = new HashMap<>();

    //check if incoming message is a command
    protected static void isCommand(GuildMessageReceivedEvent event){
        //get the first word of the message, remove the first letter (which should be prefix)
        String command = Arrays.stream(event.getMessage().getContentRaw().split("\\s+")).findFirst().get().substring(1);
        //check if the word is a command or an alias of a command and if the whole message begins with prefix
        if ((commands.containsKey(command) || aliases.containsKey(command)) && event.getMessage().getContentRaw().startsWith(PREFIX))
            //execute command if it is a command
            exe(command, event.getGuild(), event.getAuthor(), event.getChannel(), event.getMessage());
    }

    //load the created commands (only done once for initialization of bot)
    private static void loadCommand(){
        //reflection for all class that extends defaultcommand in main.command
        Reflections reflections = new Reflections("Main.Command");
        Set<Class<? extends DefaultCommand>> com = reflections.getSubTypesOf(DefaultCommand.class);

        //for each class that extends defaultcommand
        for(Class<? extends DefaultCommand> c : com){
            try{
                if(Modifier.isAbstract(c.getModifiers())) continue;
                DefaultCommand command = c.getConstructor().newInstance();
                //if the command is not already registered, register it as an available command
                if(!commands.containsKey(command.getCommand())) commands.put(command.getCommand(), command);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
                e.printStackTrace();
            }
        }
    }

    //load the aliases of the created commands (only done once for initialization of bot)
    private static void loadAlias(){
        //for each command registered, put its alias into a different map with the corresponding command object
        commands.values().forEach(c -> aliases.put(c.getAlias(), c));
    }

    //get a command object based on a string name of command
    public static DefaultCommand getCommand(String com){
        //if the string is a command, find it in commands map
        if(commands.containsKey(com)) return commands.get(com);
        //if the string is a alias, find it in alias map
        return aliases.get(com);
    }

    //get the map of all commands registered
    public static HashMap<String, DefaultCommand> getCommands(){
        return commands;
    }

    //execution function for commands
    public static void exe(String com, Guild guild, User user, TextChannel tc, Message msg){
        try {
            //get the command object and call the exe function of that command
            getCommand(com).exe(guild, user, tc, msg);
        } catch (HierarchyException e){
            //permission error
            tc.sendMessage("I do not have the permissions to do that!").queue();
        } catch (IllegalArgumentException e){
            //if member is null error occurs, send this message
            if(e.getMessage().equals("Member may not be null")) tc.sendMessage("Mention a valid user.").queue();
        }
    }

    //initialize command and alias, done once when bot starts
    public static void init(){
        loadCommand();
        loadAlias();
    }
}
