package network.pxl8.colouredchat.lib;

import net.minecraft.util.text.TextFormatting;
import network.pxl8.colouredchat.ColouredChat;
import network.pxl8.colouredchat.config.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LibColour {
    public static List getColours() {
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
        return ColouredChat.COLOURS.get(rand.nextInt(ColouredChat.COLOURS.size())).toString();
    }

    public static TextFormatting getColourFromName(String colour) {
        switch (colour) {
            case("DARK_GREEN"): return TextFormatting.DARK_GREEN;
            case("DARK_AQUA"): return TextFormatting.DARK_AQUA;
            case("DARK_RED"): return TextFormatting.DARK_RED;
            case("DARK_PURPLE"): return TextFormatting.DARK_PURPLE;
            case("GOLD"): return TextFormatting.GOLD;
            case("BLUE"): return TextFormatting.BLUE;
            case("GREEN"): return TextFormatting.GREEN;
            case("AQUA"): return TextFormatting.AQUA;
            case("RED"): return TextFormatting.RED;
            case("LIGHT_PURPLE"): return TextFormatting.LIGHT_PURPLE;
            case("YELLOW"): return TextFormatting.YELLOW;
        }
        return null;
    }
}