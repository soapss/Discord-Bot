package Main.Command.General;

import Main.Command.CommandCategory;
import Main.Command.DefaultCommand;
import Main.Command.PageMap;
import Main.Handler.CommandHandler;
import Main.Handler.PageManager;
import Main.Misc.Emoji;
import Main.Util.Embed;
import Main.Util.MessageProcessor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Comparator.comparing;

public class Help extends GeneralCommand {

    public Help() {
        super();
    }

    @Override
    public String getCommand() {
        return "help";
    }

    @Override
    public String getDes() {
        return "(command)";
    }

    //category of super, which is general
    @Override
    public CommandCategory getCat() {
        return super.getCat();
    }

    @Override
    public String getAlias() {
        return "?";
    }

    @Override
    public void exe(Guild guild, User user, TextChannel tc, Message msg) {
        //get message without command
        String query = MessageProcessor.excludeCom(msg);
        //find command based on message
        DefaultCommand command = CommandHandler.getCommand(query);

        //if user included a command in the message
        if(!query.trim().isEmpty()) {
            //send command usage info based on command found above
            EmbedBuilder info = Embed.embed();
            info.setTitle("Usage").setDescription(String.format("`~%s/%s` %s", command.getCommand(), command.getAlias(), command.getDes())).setFooter("[required] (optional)");
            tc.sendMessage(info.build()).queue();
            return;
        }

        //if user did not include a command
        //set pages for pages of different commands
        HashMap<Integer, MessageEmbed> pages = setPages();
        //open user dm
        PrivateChannel channel = user.openPrivateChannel().complete();
        //log the new page command
        PageManager.getPrinter(channel).newPageOf(CommandHandler.getCommand("help"),pages);
        //send the pages and react with options for navigation
        channel.sendMessage(pages.get(1)).queue(m -> {
            m.addReaction(Emoji.ARROW_LEFT.getEmoji()).queue();
            m.addReaction(Emoji.ARROW_RIGHT.getEmoji()).queue();
            //log the message id for future changes
            PageMap.setCommand(m.getId(), CommandHandler.getCommand(getCommand()));
        });
        //react to command
        msg.addReaction(Emoji.OK.getEmoji()).queue();
    }

    //set up the pages
    private HashMap<Integer, MessageEmbed> setPages(){
        //map for mapping the page number to each page
        HashMap<Integer, MessageEmbed> pages = new HashMap<>();
        //starting page number
        AtomicInteger page = new AtomicInteger(1);

        //for each command category there is
        for(CommandCategory cc : CommandCategory.values()){
            //make a list to store all commands under the command category
            List<DefaultCommand> com = new LinkedList<>();
            //for each command, if the command has the category, then add it to the list
            for(DefaultCommand c : CommandHandler.getCommands().values()) if (c.getCat().equals(cc)) com.add(c);
            com.sort(comparing(DefaultCommand::getCommand));

            //for printing
            StringJoiner joiner = new StringJoiner("\n");
            EmbedBuilder builder = Embed.embed();

            //for each command, print command name, alias, and description
            for(DefaultCommand c : com)
                joiner.add(String.format("`~%s/%s` %s", c.getCommand(), c.getAlias(), c.getDes()));

            //add page title and page footer
            builder.setTitle(com.get(0).getCat().getCategory() + " Commands").setDescription(joiner.toString()).setFooter(String.format("Page %d/%d",page, CommandCategory.values().length));
            pages.put(page.getAndIncrement(),builder.build());
        }
        //return pages
        return pages;
    }
}
