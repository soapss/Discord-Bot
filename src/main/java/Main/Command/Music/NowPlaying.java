package Main.Command.Music;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import Main.Music.MusicManager;
import Main.Util.Embed;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class NowPlaying extends MusicCommand {
    public NowPlaying() {
        super();
    }

    @Override
    public String getCommand() {
        return "current";
    }

    @Override
    public String getDes() {
        return "";
    }

    //category of super, which is music
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "np";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {

        //if player of server is not playing
        if(!MusicManager.getTrackScheduler(guild).isPlaying()) {
            tc.sendMessage("Nothing is playing at the moment.").queue();
            return;
        }

        //get currently playing track
        AudioTrack track = MusicManager.getTrackScheduler(guild).getPlayer().getPlayingTrack();

        //format an embed and send it
        EmbedBuilder em = Embed.embed();
        em.setTitle("Now playing");
        em.setDescription(String.format("[%s](%s)\n%s %s/%s",
                track.getInfo().title,
                String.format("https://www.youtube.com/watch?v=%s",track.getIdentifier()),
                getProgress(track),
                getTime(track.getPosition()),
                getTime(track.getDuration())));
        em.setThumbnail(String.format("https://i.ytimg.com/vi/%s/0.jpg",track.getIdentifier()));
        tc.sendMessage(em.build()).queue();
    }

    //format time to minutes and seconds from milliseceonds
    private String getTime(long time){
        return String.format("%dm %ds",time/60000,(time%60000)/1000);
    }

    //get percentage progress for the duration of the track
    private String getProgress(AudioTrack track){

        double progress = (double) track.getPosition() / track.getDuration() * 10;

        //string builder to append strings
        StringBuilder builder = new StringBuilder();

        //bar and dot to represent progreess
        for(int i = 0; i < Math.round(progress); i++){
            builder.append(Emoji.LINE.getEmoji());
        }
        builder.append(Emoji.MARKER.getEmoji());

        for(int i = 0; i < 10 - Math.round(progress); i++){
            builder.append(Emoji.LINE.getEmoji());
        }

        //return progress bar
        return builder.toString();
    }
}
