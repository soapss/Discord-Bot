package Main.Command.Music;

import Main.Command.CommandCategory;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

public class Leave extends MusicCommand {

    public Leave() {
        super();
    }

    @Override
    public String getCommand() {
        return "leave";
    }

    @Override
    public String getDes() {
        return "";
    }

    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "l";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //check if self is connected
        if(guild.getSelfMember().getVoiceState().getChannel() == null) {
            tc.sendMessage("I am not connected to a voice channel!").queue();
            return;
        }

        //dc from vc
        AudioManager audioManager = guild.getAudioManager();
        audioManager.closeAudioConnection();
        //send message
        tc.sendMessage("Disconnected from the voice channel!").queue();
    }
}
