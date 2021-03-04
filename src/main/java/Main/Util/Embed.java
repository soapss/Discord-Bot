package Main.Util;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.Color;
import java.util.Random;

public class Embed {

    //not necessary but basically creates an embed aka fancy message with random color banners each time
    public static EmbedBuilder embed(){
        //new builder
        EmbedBuilder em = new EmbedBuilder();
        //randomizer
        Random rand = new Random();
        //create a color based on randomized integer RGB values
        Color col = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
        //set the embed color band to the random color
        em.setColor(col);

        //return the embed
        return em;
    }

}
