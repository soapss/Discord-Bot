package Main.User;

import java.util.Random;

//anything related to currency for the bot
public class Econ {
    //how much user get for ~daily
    private static final short DEFAULT_DAILY = 50;

    //get daily command for user with userid as input
    public static void getDaily(String userID){
        //calls set econ which adds the default daily amount to user amount
        UserManager.getUser(userID).setEcon(DEFAULT_DAILY);
    }

    //under construction
    public static void gamble(long amount){

    }

    //never tested, basically coded a scam lol
    //should change if you want
    //super bad code, should delete if you dont use it
    public static String coinBet(String userID, long amount, String bet){
        //get user object based on user id
        User user = UserManager.getUser(userID);
        //boolean version of bet, true for head and false for tails
        boolean condition;
        //opposite to user bet
        String op;

        if(bet.equals("heads")) {
            condition = true;
            op = "tails";
        }
        else {
            condition = false;
            op = "heads";
        }

        //if value from flip is equal to condition
        if(flip() == condition) {
            //add user bet amount to user econ
            user.setEcon(amount);
            //return result message
            return String.format("The coin landed on %s! You received %d omos!", bet, amount);
        }
        else {
            //deduct user bet amount from user econ
            user.setEcon(-amount);
            //return result message
            return String.format("The coin landed on %s! You lost %d omos!", op, amount);
        }
    }

    //flip coin function aka randomizer again
    private static boolean flip(){
        //new randomizer
        Random rand = new Random();
        //random number from 0-1 (bound is non inclusive)
        if(rand.nextInt(2) == 0)
            return true;
        else
            return false;
    }
}
