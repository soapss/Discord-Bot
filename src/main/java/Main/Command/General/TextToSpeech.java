package Main.Command.General;

import Main.Command.CommandCategory;
import Main.Handler.CommandHandler;
import Main.Util.MessageProcessor;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.*;
//does not work lol
public class TextToSpeech extends GeneralCommand{
    public TextToSpeech() {
        super();
    }

    @Override
    public String getCommand() {
        return "tts";
    }

    @Override
    public String getDes() {
        return "[text]";
    }

    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        if(isConnected(guild, user))
            CommandHandler.exe("join", guild, user, tc, msg);

        MessageBuilder m = new MessageBuilder();
        m.setContent(user.getName() + " said " + MessageProcessor.excludeCom(msg)).setTTS(true);
        tc.sendMessage(m.build()).queue();
    }

    private static boolean isConnected(Guild guild, User user){
        return guild.getSelfMember().getVoiceState().getChannel() == null ||
                guild.getSelfMember().getVoiceState().getChannel() != guild.getMember(user).getVoiceState().getChannel();
    }
}
