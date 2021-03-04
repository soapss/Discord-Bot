package Main.Command.General;

import Main.Command.CommandCategory;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.Arrays;
import java.util.Optional;

import static java.lang.Integer.parseInt;

public class Rng extends GeneralCommand{

    //default range for rng
    private static final int DEFAULT_MIN = 0;
    private static final int DEFAULT_MAX = 100;

    public Rng() {
        super();
    }

    @Override
    public String getCommand() {
        return "rng";
    }

    @Override
    public String getDes() {
        return "(min) (max)";
    }

    //category of super, which is general
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "random";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //to store random number
        int num;

        Optional<String> min = Arrays.stream(msg.getContentRaw().split("\\s+")).skip(1).findFirst();
        Optional<String> max = Arrays.stream(msg.getContentRaw().split("\\s+")).skip(2).findFirst();
        //if user do not add any parameters for range, use default range
        if(!min.isPresent()) {
            num = getNum(DEFAULT_MIN, DEFAULT_MAX);
        }
        else {
            try {
                //get number based on useer parameter for rangee
                num = getNum(parseInt(min.get()), parseInt(max.get()));
                //if invalid range
            } catch (NumberFormatException e){
                tc.sendMessage(String.format("Usage: ~%s %s", getCommand(), getDes())).queue();
                return;
            }
        }
        //print number
        tc.sendMessage(String.format("`%d`", num)).queue();
    }

    //rng generator based on range
    private static int getNum(int min, int max){
        return (int) (Math.random() * (max - min + 1) + min);
    }
}
