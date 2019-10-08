package network.pxl8.colouredchat.lib;

import net.minecraft.util.text.TextFormatting;
import network.pxl8.colouredchat.ColouredChat;
import network.pxl8.colouredchat.config.Configuration;

import java.util.*;

public class LibColour {
    public static List<TextFormatting> getColours() {
        List<TextFormatting> colours = new ArrayList<>();

        if (Configuration.DARK_GREEN.get()){colours.add(TextFormatting.DARK_GREEN);}
        if (Configuration.DARK_AQUA.get()){colours.add(TextFormatting.DARK_AQUA);}
        if (Configuration.DARK_RED.get()){colours.add(TextFormatting.DARK_RED);}
        if (Configuration.DARK_PURPLE.get()){colours.add(TextFormatting.DARK_PURPLE);}
        if (Configuration.GOLD.get()){colours.add(TextFormatting.GOLD);}
        if (Configuration.BLUE.get()){colours.add(TextFormatting.BLUE);}
        if (Configuration.GREEN.get()){colours.add(TextFormatting.GREEN);}
        if (Configuration.AQUA.get()){colours.add(TextFormatting.AQUA);}
        if (Configuration.RED.get()){colours.add(TextFormatting.RED);}
        if (Configuration.LIGHT_PURPLE.get()){colours.add(TextFormatting.LIGHT_PURPLE);}
        if (Configuration.YELLOW.get()){colours.add(TextFormatting.YELLOW);}

        return colours;
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

    public static TextFormatting randomFormattedColour() {
        Random rand = new Random();
        return ColouredChat.COLOURS.get(rand.nextInt((ColouredChat.COLOURS.size())));
    }

    public static TextFormatting getRandomColourFromMap(HashMap<TextFormatting, Integer> map) {
        List<TextFormatting> leastUsedColours = new ArrayList<>();

        Iterator<Integer> mapValues = map.values().iterator();
        int min = mapValues.next();
        while (mapValues.hasNext()) { min = Math.min(min, mapValues.next()); }

        for (TextFormatting colour : map.keySet()) {
            if (map.get(colour).equals(min)) {
                leastUsedColours.add(colour);
            }
        }
        leastUsedColours.remove(ColouredChat.LAST_COLOUR);

        Random rand = new Random();
        TextFormatting colour = leastUsedColours.get(rand.nextInt(leastUsedColours.size()));
        map.put(colour, map.get(colour) + 1);
        ColouredChat.LAST_COLOUR = colour;
        return colour;
    }

    public static void removeColourFromMap(HashMap<TextFormatting, Integer> map, TextFormatting colour) {
        if (map.get(colour) > 0) { map.put(colour, map.get(colour) - 1); }
    }
}