package network.pxl8.colouredchat.lib;

import net.minecraft.util.text.TextFormatting;
import network.pxl8.colouredchat.config.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LibColour {
    private static List getColours() {
        List<TextFormatting> colours = new ArrayList<>();

        if (Configuration.colour_config.DARK_GREEN){colours.add(TextFormatting.DARK_GREEN);}
        if (Configuration.colour_config.DARK_AQUA){colours.add(TextFormatting.DARK_AQUA);}
        if (Configuration.colour_config.DARK_RED){colours.add(TextFormatting.DARK_RED);}
        if (Configuration.colour_config.DARK_PURPLE){colours.add(TextFormatting.DARK_PURPLE);}
        if (Configuration.colour_config.GOLD){colours.add(TextFormatting.GOLD);}
        if (Configuration.colour_config.BLUE){colours.add(TextFormatting.BLUE);}
        if (Configuration.colour_config.GREEN){colours.add(TextFormatting.GREEN);}
        if (Configuration.colour_config.AQUA){colours.add(TextFormatting.AQUA);}
        if (Configuration.colour_config.RED){colours.add(TextFormatting.RED);}
        if (Configuration.colour_config.LIGHT_PURPLE){colours.add(TextFormatting.LIGHT_PURPLE);}
        if (Configuration.colour_config.YELLOW){colours.add(TextFormatting.YELLOW);}

        return colours;
    }

    public static String randomColour() {
        Random rand = new Random();
        return getColours().get(rand.nextInt(getColours().size())).toString();
    }

    public static String getColourFromName(String colour) {
        switch (colour) {
            case("DARK_GREEN"): return getColours().get(0).toString();
            case("DARK_AQUA"): return getColours().get(1).toString();
            case("DARK_RED"): return getColours().get(2).toString();
            case("DARK_PURPLE"): return getColours().get(3).toString();
            case("GOLD"): return getColours().get(4).toString();
            case("BLUE"): return getColours().get(5).toString();
            case("GREEN"): return getColours().get(6).toString();
            case("AQUA"): return getColours().get(7).toString();
            case("RED"): return getColours().get(8).toString();
            case("LIGHT_PURPLE"): return getColours().get(9).toString();
            case("YELLOW"): return getColours().get(10).toString();
        }
        return null;
    }
}
