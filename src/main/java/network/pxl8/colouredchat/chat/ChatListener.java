package network.pxl8.colouredchat.chat;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.text.*;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.pxl8.colouredchat.ColouredChat;
import network.pxl8.colouredchat.config.Configuration;
import network.pxl8.colouredchat.lib.LibColour;

import java.util.*;

@Mod.EventBusSubscriber
public class ChatListener {

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        ColouredChat.getCap(event.getPlayer()).ifPresent(colourData -> {
            if (Configuration.USE_QUASI_RANDOM_ASSIGNMENT.get()) {
                TextFormatting colour = LibColour.getRandomColourFromMap(ColouredChat.COLOUR_MAP);
                colourData.setRandomColour(colour);
                colourData.setQuasiRandomColour(colour);
            } else {
                colourData.setRandomColour(LibColour.randomFormattedColour());
            }
            if (Configuration.USE_CUSTOM_TEAM_ASSIGNMENT.get()) {
                Scoreboard sb = event.getPlayer().getWorldScoreboard();
                String playerName = event.getPlayer().getDisplayName().getString();
                String shortName = playerName.substring(0, Math.min(playerName.length(), 8));

                ImmutableList<ScorePlayerTeam> teams = ImmutableList.copyOf(sb.getTeams());
                teams.forEach(team -> { if (team.getName().equals(shortName + "_colchat")) sb.removeTeam(team); });

                if (colourData.getUsePlayerColour()) {
                    ScorePlayerTeam playerTeam = sb.createTeam(shortName + "_colchat");
                    playerTeam.setColor(colourData.getPlayerColour());
                    sb.addPlayerToTeam(playerName, playerTeam);
                } else {
                    ScorePlayerTeam playerTeam = sb.createTeam(shortName + "_colchat");
                    playerTeam.setColor(colourData.getRandomColour());
                    sb.addPlayerToTeam(playerName, playerTeam);
                }
            }
        });
    }
    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        Scoreboard sb = event.getPlayer().getWorldScoreboard();
        String playerName = event.getPlayer().getDisplayName().getString();
        String shortName = playerName.substring(0, Math.min(playerName.length(), 8));

        ImmutableList<ScorePlayerTeam> teams = ImmutableList.copyOf(sb.getTeams());
        teams.forEach((team -> {if (team.getName().equals(shortName + "_colchat")) sb.removeTeam(team);}));

        if (Configuration.USE_QUASI_RANDOM_ASSIGNMENT.get()) {
            ColouredChat.getCap(event.getPlayer()).ifPresent(colourData -> LibColour.removeColourFromMap(ColouredChat.COLOUR_MAP, colourData.getQuasiRandomColour()));
        }
    }

    @SubscribeEvent
    public static void onServerMsg(ServerChatEvent event) {
        JsonObject textComponentJSON = TextComponent.Serializer.toJsonTree(event.getComponent()).getAsJsonObject();
        ITextComponent msgTextComponent = ITextComponent.Serializer.fromJson(textComponentJSON.get("with"));
        Iterator<ITextComponent> textComponentSib = msgTextComponent.getSiblings().iterator();

        ITextComponent newTextComponent = new StringTextComponent(Configuration.DELIMITER_LEFT.get());
        ColouredChat.getCap(event.getPlayer()).ifPresent(colourData -> {
            if (colourData.getUsePlayerColour()) {
                newTextComponent.appendSibling(textComponentSib.next().setStyle(msgTextComponent.getStyle()).applyTextStyle(colourData.getPlayerColour()));
            } else {
                newTextComponent.appendSibling(textComponentSib.next().setStyle(msgTextComponent.getStyle()).applyTextStyle(colourData.getRandomColour()));
            }
        });
        newTextComponent.appendText(Configuration.DELIMITER_RIGHT.get());

        while (textComponentSib.hasNext()) {
            newTextComponent.appendSibling(textComponentSib.next());
        }

        event.setComponent(newTextComponent);
    }
}