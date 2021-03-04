package Main;

import Main.Handler.EventHandler;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

//this is basically the class to run
public class Main {
    //the string args is the bot key
    public static void main(String[] args) throws LoginException {
        //agrs[0] is the string args
        init(args[0]);
        //for termination command, make a new thread to do the update, which is storing user data for the econ and user commands in a text file (done in a different class)
        Thread update = new Thread(Update::getUpdate);
        Runtime.getRuntime().addShutdownHook(update);
    }
//function to create the bot
    public static void init(String token) throws LoginException {
        //new builder object with bot key as input
        JDABuilder builder =  JDABuilder.createDefault(token);
        //need this to listen to activities (ex. messages in guilds aka servers, reactions, etc.)
        builder.addEventListeners(new EventHandler());
        //bot status
        builder.setActivity(Activity.playing("~help"));
        //builds the bot
        builder.build();
        //function in Init class to initialize different bot functions
        Init.init();
    }
}

