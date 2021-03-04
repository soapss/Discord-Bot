package Main;

import Main.User.User;
import Main.User.UserManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;
//the update class to store data after the bot is terminated
public class Update {

    //a public get function for updating new  data
    public static void getUpdate(){
        updateUser();
    }

    //a public get function for loading existing data
    public static void load(){
        loadUser();
    }

    //update data function
    private static void updateUser(){
        try{
            //create file object to store data
            File user = new File("users.txt");
            user.createNewFile();
            //new writer object to write data in file
            FileWriter write = new FileWriter("users.txt");
            //write the data
            write.write(UserManager.getUpdate());
            //closes writer
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //load data function
    private static void loadUser(){
        try {
            //create file object or the destination for the scanner to scan the data
            File user = new File("users.txt");
            //new scanner object
            Scanner sc = new Scanner(user);
            //while there is a next line
            while (sc.hasNextLine()) {
                //scan the data delimited by space
               String[] data = sc.nextLine().split(" ");
               //creates new user object based on user info in first index
               User u = new User(data[0]);
               //econ or currency is stored in second index
               u.setEcon(Long.parseLong(data[1]));
               //exp is stored in third index
               u.setExp(Long.parseLong(data[2]));
               //rep point is stored in forth index
               u.setRep(Long.parseLong(data[3]));
               //time stamp for last ~daily command used by user is stored in fifth index
               u.setDailyTime(LocalDateTime.parse(data[4]));
               //time stamp for last ~rep command used by user is stored in sixth index
               u.setRepTime(LocalDateTime.parse(data[5]));
               //passes the new user object to user manager
               UserManager.newUser(u);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
