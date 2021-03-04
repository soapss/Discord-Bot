package Main.Handler;

import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.HashMap;

//basically the music manager of trackscheduler but for printer
public class PageManager {

    private final Printer printer;
    //map each printer to a designated message channel (aka new printer per channel)
    private static HashMap<MessageChannel, Printer> printers = new HashMap<>();

    //object creation
    private PageManager(MessageChannel channel){
        printer = new Printer();
        printers.put(channel, printer);
    }

    //get printer for message channel
    public static Printer getPrinter(MessageChannel channel) {
        //if channel dne, return null
        if (channel == null) return null;
        //if printer is available for channel, return printer
        if (printers.containsKey(channel)) return printers.get(channel);
        //if no printer is available, make one
        else return new PageManager(channel).printer;
    }


}
