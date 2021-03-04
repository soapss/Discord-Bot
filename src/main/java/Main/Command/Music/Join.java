package Main.Command.Music;

import Main.Command.CommandCategory;
import Main.Misc.Emoji;
import Main.Music.MusicManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

public class Join extends MusicCommand {

    public Join() {
        super();
    }

    @Override
    public String getCommand() {
        return "join";
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
        return "j";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //check if user and self are in vc
        if(!userVoiceStatus(guild, user, tc) || selfVoiceStatus(guild, user, tc)) return;

        //get the vc user is in
        VoiceChannel vc = guild.getMember(user).getVoiceState().getChannel();
        //connect to vc
        AudioManager audioManager = guild.getAudioManager();
        audioManager.openAudioConnection(vc);
        //set music channel to textchannel of message for future messages
        MusicManager.getTrackScheduler(guild).setTc(tc);
        //react ok to command
        msg.addReaction(Emoji.OK.getEmoji()).queue();
    }

    private boolean selfVoiceStatus(Guild guild, User user, TextChannel tc){
        //get vc for self and user
        VoiceChannel selfVoice = guild.getSelfMember().getVoiceState().getChannel();
        VoiceChannel userVoice = guild.getMember(user).getVoiceState().getChannel();

        //if self not in vc or self and user are in different vc, return false
        if(selfVoice == null || selfVoice != userVoice) return false;

        tc.sendMessage("I am already in the voice channel!").queue();
        return true;
    }


    private boolean userVoiceStatus(Guild guild, User user, TextChannel tc){
        //if user is not in vc
        if(!guild.getMember(user).getVoiceState().inVoiceChannel()){
            tc.sendMessage("You are not connected to a voice channel!").queue();
            return false;
        }
        //get vc of user
        VoiceChannel userVoice = guild.getMember(user).getVoiceState().getChannel();
        //return result of check self perm to join vc
        return checkVoicePerm(guild, tc, userVoice);
    }

    private boolean checkVoicePerm(Guild guild, TextChannel tc, VoiceChannel vc){
        if(vc == null) return false;

        //if self has perm
        if(guild.getSelfMember().hasPermission(vc, Permission.VOICE_CONNECT) &&
                guild.getSelfMember().hasPermission(vc, Permission.VOICE_SPEAK))
            return true;

        tc.sendMessage("I do not have permissions to join the voice channel you are in!").queue();
        return false;
    }
}
