package Main.Handler;

import Main.Music.MusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Timer;
import java.util.TimerTask;

//handles stuff that happens
public class EventHandler extends ListenerAdapter {

    //when message is sent in server
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        //if sent by bot, ignore
        if(event.getAuthor().isBot()) return;
        //check if the message is a command
        CommandHandler.isCommand(event);
    }

    //when message is sent in dm
    //not completed yet
    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        //if sent by bot, ignore
        if(event.getAuthor().isBot()) return;
        //add more here
    }

    //when a reaction is registered
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        //if sent by bot, ignore
        if(event.getUser().isBot()) return;
        //if message that the reaction was added to was not sent by self, ignore
        if(!event.getChannel().retrieveMessageById(event.getMessageId()).complete().getAuthor().equals(event.getChannel().getJDA().getSelfUser())) return;

        //call printer for the channel to update based on event
        PageManager.getPrinter(event.getChannel()).update(event);
    }

    //when someone joins vc
    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event){
        //get user that joined
        Member member = event.getMember();
        //if user is self, deafen self
        //this is a temporary solution to deafen like groovy does, the intention is to not get user voice data, but i think its supposed to be done with intents
        if(member.equals(event.getGuild().getSelfMember())) event.getGuild().deafen(member, true).queue();
    }

    //when someone leaves vc
    @Override
    public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent event) {
        //get self and guild object
        Member self = event.getGuild().getSelfMember();
        Guild guild = event.getGuild();

        //if that person who leaves is self, clear the music queue stuff for that server
        if(event.getMember().equals(self)) {
            MusicManager.getTrackScheduler(guild).clear();
            return;
        }

        //if self is not in vc, return
        if(!self.getVoiceState().inVoiceChannel()) return;

        //check if self can leave
        if(canLeave(self)) {
            //set up timer for afk channel
            Timer t = new Timer();
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    //if self still can leave when timer finishes
                    if(canLeave(self)) {
                        //leave vc and send message
                        guild.getAudioManager().closeAudioConnection();
                        guild.getDefaultChannel().sendMessage("I've been alone in this voice channel for too long!").queue();
                    }
                }
            };
            //schedule timer with delay
            t.schedule(tt, 90000);
        }
    }

    //if self is the only one in vc
    private boolean canLeave(Member self){
        return self.getVoiceState().getChannel().getMembers().size() == 1;
    }
}
