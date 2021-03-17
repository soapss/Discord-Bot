package Main.Util;

import net.dv8tion.jda.api.entities.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

//this is supposed to process the message send by user
//can be optimized and made smarter but idk how atm

public class MessageProcessor {

    //split the message by space and return an array with each word as an index
    public static String[] processMsg(Message msg){
        return msg.getContentRaw().split("\\s+");
    }

    //exclude command from the message
    //don't know why it's so complicated by basically removes the first word from the message lol
    public static String excludeCom(Message msg){
        //make the array from the processMsg function into a list
        List<String> Message = new ArrayList<>(Arrays.asList(processMsg(msg)));

        //string joiner basically appends strings by delimiter, which in this case is a space
        StringJoiner joiner = new StringJoiner(" ");
        //make the list into a stream
        Stream<String> stream  = Message.stream();
        //skip the first one in the stream and for each word after the first, add it with the string joiner
        stream.skip(1).forEach(joiner::add);

        //return string made by string joiner
        return joiner.toString();
    }

}
