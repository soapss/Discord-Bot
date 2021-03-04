package Main.Command;

import java.util.HashMap;

//mapping all the messageid to commands
public class PageMap {

    //map to map all page printing messages to the context (the command that printed message)
    private static HashMap<String, DefaultCommand> messages = new HashMap<>();

    //get command object for messageid
    public static DefaultCommand hasCommand(String msgID){
        return messages.get(msgID);
    }

    //set a new value in the map with msgid and corresponding command
    public static void setCommand(String msgID, DefaultCommand command){
        messages.put(msgID, command);
    }

}
