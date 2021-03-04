package Main.Music;

import Main.Util.Embed;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.commons.collections4.ListUtils;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

//basically the music player for each server
public class TrackScheduler extends AudioEventAdapter implements AudioLoadResultHandler{

    //player object
    private final AudioPlayer player;
    //a queue for songs left to play
    private LinkedBlockingQueue<AudioTrack> queue;
    //a list for songs already played
    private final List<AudioTrack> polled;
    //for queue printing, integer is for page number, list of audiotrack is the list of songs for each page
    private HashMap<Integer, List<AudioTrack>> pages;
    //current page number for the queue displayed
    private AtomicInteger page;
    //music channel for server to send messages to
    private TextChannel mc;
    //last sent automated message id
    private String msgId;
    //statuses set by users for announcement, loop song, and loop entire queue
    private boolean announcement = true;
    private boolean loopSong = false;
    private boolean loopQ = false;

    //trackscheduler object
    TrackScheduler(AudioPlayer player) {
        this.player = player;
        queue = new LinkedBlockingQueue<>();
        polled = new LinkedList<>();
    }

    //when a track is loaded
    @Override
    public void trackLoaded(AudioTrack track) {
        //add to queue
        queue.offer(track);
        //if the player is not playing, play the song next in queue
        if(!isPlaying()) next();
    }

    //when a playlist is loaded
    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        //for each track in playlist, add to queue
        for(AudioTrack t : playlist.getTracks()) queue.offer(t);
        //if the player isnt playing, play the song next in queue.
        if(!isPlaying()) next();
    }

    //can add messages if you want
    @Override
    public void noMatches() {

    }

    //can add messages if you want
    @Override
    public void loadFailed(FriendlyException exception) {

    }

    //when playing track ends
    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        //if announcement is on, delete last announcement
        if(announcement) mc.deleteMessageById(msgId).queue();
        //if the track ends naturally (ex. not ended by stop or skip)
        if(endReason.mayStartNext) {
            //add to songs already played
            polled.add(track);
            //if loopsong is on, call previous function to play last song
            if(loopSong){
                previous();
                return;
            }
            //if queue is empty and loop queue is on
            if(queue.isEmpty() && loopQ){
                //make a list to store a copy of the current queue
                LinkedList<AudioTrack> copyQ = new LinkedList<>();
                //for each track in polled make a copy of it and store it in the new list
                for(AudioTrack t : polled) copyQ.add(t.makeClone());
                //clear polled
                polled.clear();
                //set queue to the list of cloned songs
                queue = new LinkedBlockingQueue<>(copyQ);
            }
            //play next song
            next();
        }
    }

    //when a track starts
    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        //if announcement is on
        if(announcement) {
            //create an embed and display the now playing message
            EmbedBuilder em = Embed.embed();
            em.setTitle("Now playing").setDescription(getPlayer().getPlayingTrack().getInfo().title);
            //store it as the msg to delete before the next announcement
            mc.sendMessage(em.build()).queue(msg -> msgId = msg.getId());
        }
    }

    //to set announcement status
    public void announce(boolean status){
        announcement = status;
    }

    //clear function for polled, queue, and stops the player from playing songs
    public void clear(){
        polled.clear();
        queue.clear();
        player.stopTrack();
    }

    //to jump to different places in the entire queue (polled + queue)
    public boolean jump(int index){
        //if the player is playing, add the playing song to polled list
        if(isPlaying()) polled.add(player.getPlayingTrack());
        //current place in entire queue
        int p = polled.size();

        //if the index entered by user is over or undersized, return
        if(index < 1 || index > p + queue.size()) return false;

        //if the jump index is the current place in entire queue, play the song just added to pollede
        if(p == index) previous();
        //if the jump index is less than current place
        else if(index < p) {
            //make new queue list with items in the old queue
            LinkedList<AudioTrack> newQ = new LinkedList<>(queue);
            //some math stuff that loads back the polled songs into the queue
            for (; p > index; p--) {
                newQ.add(0,polled.get(polled.size()-1).makeClone());
                polled.remove(polled.get(polled.size()-1));
            }
            //set the queue to the new queue list
            queue = new LinkedBlockingQueue<>(newQ);
            //if the jump index is greater  than current place, pop the queue items into the polled list directly
        } else for (; p < index; p++) polled.add(queue.poll());

        //play next song
        next();
        return true;
    }

    //set loop song status
    public void loopSong(boolean status){
        //turn loop queue off
        loopQ = false;
        loopSong = status;
    }

    //set loop queue status
    public void loopQ(boolean status){
        //turn loop song off
        loopSong = false;
        loopQ = status;
    }

    //to play next song
    private void next(){
        //if theres available songs in queue, play the next song in queue
        if(queue.peek() != null) player.playTrack(queue.poll());
    }


    //to play previous song
    public void previous(){
        //create a new list for new queue with the items in the old queue
        LinkedList<AudioTrack> newQ = new LinkedList<>(queue);
        //if the player is playing, add the playing track to polled list
        if(isPlaying()) polled.add(getPlayer().getPlayingTrack());
        //add the last song in polled list to the from of the new list of songs in queue
        newQ.add(0,polled.get(polled.size()-1).makeClone());
        //set current queue to new queue list
        queue = new LinkedBlockingQueue<>(newQ);
        //remove the last song in polled list
        polled.remove(polled.get(polled.size()-1));
        //play next song
        next();
    }

    //printing the queue
    public String printQ(){
        try {
            //if the queue is empty, print it
            if (isEmpty()) return "```fix\nQueue is empty.```";
        }catch (NullPointerException e){
            //if the player object does not exist, print it (should change it to if rather than try and catch because it takes up more time to execute)
            return "```fix\nUse ~play to queue music.```";
        }

        //call set pages to prepare the queue pages
        setPages();
        //page of current playing song
        page = new AtomicInteger((polled.size())/10);

        //return the page of the queue of the current playing song
        return String.format("```json\n%s```", getPage(page.get()));
    }

    //remove a song from queue
    public String remove(int index) throws NullPointerException{
        //calls function to find the track at index
        AudioTrack track = trackAtIndex(index);

        //if track is in polled, remove track from polled
        if(polled.contains(track)) polled.remove(track);
        //if track is in queue, remove track from queue
        else if(queue.contains(track)) queue.remove(trackAtIndex(index));
        //if track is currently playing, go next in queue without adding in polled
        else if(getPlayer().getPlayingTrack().equals(track)) next();

        //return message
        return String.format("%s is removed from the queue.", track.getInfo().title);
    }

    //set music channel to send messages to
    public void setTc(TextChannel t){ mc = t; }

    //shuffling the queue
    public void shuffle(){
        //save the queue into a list
        LinkedList<AudioTrack> newQ = new LinkedList<>(queue);
        //shuffle the list
        Collections.shuffle(newQ);
        //set the queue to the new list
        queue = new LinkedBlockingQueue<>(newQ);
    }

    //skip to next song
    public boolean skip(){
        //disable loop song
        loopSong = false;
        //set the position to the duration (end the song naturally)
        getPlayer().getPlayingTrack().setPosition(getPlayer().getPlayingTrack().getDuration());
        //return status of empty queue and loop queue
        return !queue.isEmpty() || loopQ;
    }

    //find track at index
    private AudioTrack trackAtIndex(int index){
        //return the track at index of combined polled + queue
        return merge().get(index - 1);
    }

    //get player for the server
    public AudioPlayer getPlayer(){
        return player;
    }

    //whether the player is currently playing or not
    public boolean isPlaying(){
        return player.getPlayingTrack() != null;
    }

    //whether everything is empty and player is not playing
    private boolean isEmpty(){
        return polled.isEmpty() && queue.isEmpty() && !isPlaying();
    }

    //combines the polled and queue into a single list of tracks
    private List<AudioTrack> merge(){
        //a list with items from polled
        LinkedList<AudioTrack> temp = new LinkedList<>(polled);
        //if the player is playing, add the playing song to the list
        if(isPlaying()) temp.add(player.getPlayingTrack());
        //return the union of the list and queue
        return ListUtils.union(temp, new ArrayList<>(queue));
    }

    //set all the pages of the entire queue
    private void setPages(){
        //create new hashmap for maping page number to page
        pages = new HashMap<>();
        //create a list of entire queue by calling merge
        List<AudioTrack> tracks = merge();

        //total number of pages
        int maxPage = (int) Math.ceil(tracks.size()/10.0);
        //counter for while loop
        int currentPage = 0;
        //while loop to add each page to the pages map with the page number
        while(currentPage < maxPage){
            pages.put(currentPage, tracks.subList(currentPage * 10, currentPage == maxPage -1 ? tracks.size() : (currentPage + 1) * 10));
            currentPage++;
        }
    }

    //get a specific page from the pages map
    private String getPage(int page){
        //store the content of the page into a list
        List<AudioTrack> tracks = pages.get(page);

        //starting number for the first track on the page (each page has 10 songs max)
        AtomicInteger pos = new AtomicInteger(page*10+1);
        //string builder object to build the content of the page
        StringBuilder builder = new StringBuilder();

        //for each audio track, print the position in queue, the title of song (ended with ... if longer than 32 characters), and duration of song).
        for (AudioTrack t : tracks)
            builder.append(String.format("%d) %-35s%10s\n",
                    pos.getAndIncrement(),
                    (t.getInfo().title.length() > 32 ? t.getInfo().title.substring(0, 32) + "..." : t.getInfo().title),
                    processDuration(t.getDuration())));

        //return the page built by string builder
        return builder.toString();
    }

    //get next page of queue...can be made better using ifs checking for null rather than try catch
    public String getNextPage(){
        try {
            //print next page
            return "```json\n" + getPage(page.incrementAndGet()) + "```";
        }catch(NullPointerException e){
            //if next page doesnt exist, undo page number value change
            page.decrementAndGet();
        }
        return null;
    }

    //get previous page of queue...can be made better using ifs checking for null rather than try catch
    public String getPrevPage(){
        try{
            //print previous page
            return "```json\n" + getPage(page.decrementAndGet()) + "```";
        }catch(NullPointerException e){
            //if previous page doesnt exist, undo page number value change
            page.incrementAndGet();
        }
        return null;
    }

    //function to change milliseconds to mm:ss or hh:mm:ss format
    private String processDuration(long ms){
        //if the time is shorter than an hr (no hh)
        if(ms < 3600000)
            return String.format("%2d:%02d", TimeUnit.MILLISECONDS.toMinutes(ms),
                    TimeUnit.MILLISECONDS.toSeconds(ms) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms)));
        //if the time is longer
        else
            return String.format("%2d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(ms),
                    TimeUnit.MILLISECONDS.toMinutes(ms) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(ms)),
                    TimeUnit.MILLISECONDS.toSeconds(ms) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms)));
    }
}