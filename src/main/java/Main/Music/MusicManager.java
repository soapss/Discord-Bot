package Main.Music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;

//manages the player for each guild aka server
public class MusicManager {

    //new manager object
    public static final AudioPlayerManager manager = new DefaultAudioPlayerManager();
    //new trackscheduler object (see trackscheduler class)
    private final TrackScheduler scheduler;
    //a map to map each trackscheduler to each server
    private static final HashMap<Guild, TrackScheduler> schedulers = new HashMap<>();

    //default volume dont really need to make this into a variable but idk y I did this
    private final int DEFAULT_VOL = 50;

    //music manager object for input server
    private MusicManager(Guild guild){

        //make a new audio player
        AudioPlayer player = manager.createPlayer();

        //set the scheduler to a new scheduler based on the new player created above
        scheduler = new TrackScheduler(player);
        //update the map of servers to schedulers
        schedulers.put(guild, scheduler);

        //player settings
        player.addListener(scheduler);
        player.setVolume(DEFAULT_VOL);

        //sets the player as audio sender so it can play music
        guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(player));
    }

    //get trackscheduler for server
    public static TrackScheduler getTrackScheduler(Guild guild) {
        if (guild == null)
            return null;
        //if guild has trackscheduler, return it
        if (schedulers.containsKey(guild))
            return schedulers.get(guild);
        //if guild doesn't have one, make one
        else
            return new MusicManager(guild).scheduler;
    }

    //initializes music stuff, only need to run once when the bot starts
    public static void init(){
        AudioSourceManagers.registerRemoteSources(manager);
        MusicManager.manager.getConfiguration().setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);
    }

}
