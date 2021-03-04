package Main.User;

import java.util.HashMap;
import java.util.StringJoiner;

//manages all user objects stored
public class UserManager {
    //a map mapping userid to user object
    private static HashMap<String, User> users = new HashMap<>();

    //function for new user object..... can be integrated with get user function but I haven't been working on this
    public static User newUser(User user){
        //add into map
        users.put(user.getUserID(), user);
        //return user object, ik its kinda stupid cuz it doesn't do anything lol
        return user;
    }

    //function to get the user
    public static User getUser(String userID){
        //if user exists, get it
        if(users.containsKey(userID))
            return users.get(userID);
        //if not make a new one
        else
            return newUser(new User(userID));
    }

    //get user info for current running session in the format that is used in the updateUser function in the Update class
    public static String getUpdate(){
        //string joiner object to append string with new line as delimiter, for each user
        StringJoiner u = new StringJoiner("\n");

        //for each user in the user map
        for(User user : users.values()){
            //string joiner again for each user's info, delimited by space
            StringJoiner a = new StringJoiner(" ");
            //add every info
            a.add(user.getUserID())
                    .add(String.valueOf(user.getEcon()))
                    .add(String.valueOf(user.getExp()))
                    .add(String.valueOf(user.getRep()))
                    .add(user.getDailyTime().toString())
                    .add(user.getRepTime().toString());
            //add the collected info to each user
            u.add(a.toString());
            }
        //return data for all users
        return u.toString();
    }
}
