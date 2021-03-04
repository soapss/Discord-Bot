package Main;

import Main.Handler.CommandHandler;
import Main.Music.MusicManager;
import Main.Music.SpotifySearch;

public class Init {

    //don't know why I have this in another class
    //initializes everything
    public static void init(){
        //initialize commands
        CommandHandler.init();
        //initialize music player stuff
        MusicManager.init();
        //initialize spotify api stuff
        SpotifySearch.init();
        //load user data
        Update.load();
    }
}
