package network.pxl8.colouredchat.chat;

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
import network.pxl8.colouredchat.lib.LibMeta;

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

                if (colourData.getTeamID().isEmpty()) {
                    String shortName = playerName.substring(0, Math.min(playerName.length(), 8));
                    colourData.setTeamID(shortName + LibColour.randAlphanumString(8));
                    //LibMeta.LOG.debug("Creating new teamID: " + colourData.getTeamID());
                }

                LibColour.clearTeam(sb, event.getPlayer());

                if (colourData.getUsePlayerColour()) {
                    ScorePlayerTeam playerTeam = sb.createTeam(colourData.getTeamID());
                    playerTeam.setColor(colourData.getPlayerColour());
                    sb.addPlayerToTeam(playerName, playerTeam);
                } else {
                    ScorePlayerTeam playerTeam = sb.createTeam(colourData.getTeamID());
                    playerTeam.setColor(colourData.getRandomColour());
                    sb.addPlayerToTeam(playerName, playerTeam);
                }
                //LibMeta.LOG.debug("teamID: " + colourData.getTeamID());
            }
        });
    }
    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        Scoreboard sb = event.getPlayer().getWorldScoreboard();
        LibColour.clearTeam(sb, event.getPlayer());

        if (Configuration.USE_QUASI_RANDOM_ASSIGNMENT.get()) {
            ColouredChat.getCap(event.getPlayer()).ifPresent(colourData -> LibColour.removeColourFromMap(ColouredChat.COLOUR_MAP, colourData.getQuasiRandomColour()));
        }
    }

    @SubscribeEvent
    public static void onServerMsg(ServerChatEvent event) {
        JsonObject textComponentJSON = TextComponent.Serializer.toJsonTree(event.getComponent()).getAsJsonObject();
        ITextComponent msgTextComponent = ITextComponent.Serializer.fromJson(textComponentJSON.get("with"));

        if (msgTextComponent != null) {
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
        } else {
            LibMeta.LOG.warn("ColouredChat is unable to parse chat messages! Please check if another mod/plugin modifies chat.");
        }
    }
}