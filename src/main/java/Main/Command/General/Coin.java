package Main.Command.General;

import Main.Command.CommandCategory;
import Main.User.Econ;
import Main.Util.MessageProcessor;
import net.dv8tion.jda.api.entities.*;

public class Coin extends GeneralCommand{
    public Coin() {
        super();
    }

    @Override
    public String getCommand() {
        return "coin";
    }

    @Override
    public String getDes() {
        return "[bet] [amount]";
    }

    //category of super, which is general
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "flip";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //get second parameter for bet condition
        String bet = MessageProcessor.processMsg(msg)[1];
        //store condition value
        String condition;

        //accepted inputs
        if(bet.equals("h") || bet.equals("head") || bet.equals("heads"))
            condition = "heads";
        else if(bet.equals("t") || bet.equals("tail") || bet.equals("tails"))
            condition = "tails";
        else{
            //if invalid input
            tc.sendMessage("Usage: ~" + getCommand() + " " + getDes()).queue();
            return;
        }

        //store bet amount
        long amount;
        try{
            //get bet amount from third parameter and convert to int
            amount = Integer.parseInt(MessageProcessor.processMsg(msg)[2]);
        }catch (ArrayIndexOutOfBoundsException | NumberFormatException e){
            //if invalid parameter
            tc.sendMessage("Usage: ~" + getCommand() + " " + getDes()).queue();
            return;
        }
        //get user id
        String uID = user.getId();
        //call bet function in econ
        tc.sendMessage(Econ.coinBet(uID,amount, condition)).queue();
    }
}
