package network.pxl8.colouredchat.chat;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import network.pxl8.colouredchat.ColouredChat;
import network.pxl8.colouredchat.config.Configuration;
import network.pxl8.colouredchat.lib.LibColour;
import network.pxl8.colouredchat.lib.LibMeta;

import java.util.Iterator;

@Mod.EventBusSubscriber
public class ChatListener {
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (ColouredChat.hasCap(event.player)) {
            if (Configuration.experimental.USE_QUASI_RANDOM_ASSIGNMENT) {
                TextFormatting colour = LibColour.getRandomColourFromMap(ColouredChat.COLOUR_MAP);
                ColouredChat.getCap(event.player).setRandomColour(colour);
                ColouredChat.getCap(event.player).setQuasiRandomColour(colour);
            } else {
                ColouredChat.getCap(event.player).setRandomColour(LibColour.randomFormattedColour());
            }
            /*
            if (Configuration.experimental.USE_CUSTOM_TEAM_ASSIGNMENT) {
                Scoreboard sb = event.player.getWorldScoreboard();
                String playerName = event.player.getDisplayNameString();
                String shortName = playerName.substring(0, Math.min(playerName.length(), 8));

                ImmutableList<ScorePlayerTeam> teams = ImmutableList.copyOf(sb.getTeams());
                teams.forEach(team -> { if (team.getName().equals(shortName + "_colchat")) sb.removeTeam(team); });

                if (ColouredChat.getCap(event.player).getUsePlayerColour()) {
                    ScorePlayerTeam playerTeam = sb.createTeam(shortName + "_colchat");
                    playerTeam.setColor(ColouredChat.getCap(event.player).getPlayerColour());
                    sb.addPlayerToTeam(playerName, shortName + "_colchat");
                } else {
                    ScorePlayerTeam playerTeam = sb.createTeam(shortName + "_colchat");
                    playerTeam.setColor(ColouredChat.getCap(event.player).getRandomColour());
                    sb.addPlayerToTeam(playerName, shortName + "_colchat");
                }
            }
             */
        }
    }
    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        /*
        Scoreboard sb = event.player.getWorldScoreboard();
        String playerName = event.player.getDisplayNameString();
        String shortName = playerName.substring(0, Math.min(playerName.length(), 8));

        ImmutableList<ScorePlayerTeam> teams = ImmutableList.copyOf(sb.getTeams());
        teams.forEach(team -> { if (team.getName().equals(shortName + "_colchat")) sb.removeTeam(team); });
        */

        if (Configuration.experimental.USE_QUASI_RANDOM_ASSIGNMENT) {
            LibColour.removeColourFromMap(ColouredChat.COLOUR_MAP, ColouredChat.getCap(event.player).getQuasiRandomColour());
        }
    }

    @SubscribeEvent
    public void onServerMsg(ServerChatEvent event) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson GSON = gsonBuilder
                .disableHtmlEscaping()
                .registerTypeHierarchyAdapter(ITextComponent.class, new ITextComponent.Serializer())
                .registerTypeHierarchyAdapter(Style.class, new Style.Serializer())
                .registerTypeAdapterFactory(new EnumTypeAdapterFactory())
                .create();

        JsonObject textCompJSON = GSON.toJsonTree(event.getComponent()).getAsJsonObject();
        ITextComponent msgTextComp = GSON.fromJson(textCompJSON.get("with"), ITextComponent.class);

        if (msgTextComp != null) {
            Iterator<ITextComponent> textCompSib = msgTextComp.getSiblings().iterator();

            ITextComponent newTextComp = new TextComponentString(Configuration.chat_config.DELIMITER_LEFT);
            if (ColouredChat.hasCap(event.getPlayer())) {
                if (ColouredChat.getCap(event.getPlayer()).getUsePlayerColour()) {
                    newTextComp.appendSibling(textCompSib.next().setStyle(msgTextComp.getStyle().setColor(ColouredChat.getCap(event.getPlayer()).getPlayerColour())));
                } else {
                    newTextComp.appendSibling(textCompSib.next().setStyle(msgTextComp.getStyle().setColor(ColouredChat.getCap(event.getPlayer()).getRandomColour())));
                }
            }
            newTextComp.appendText(Configuration.chat_config.DELIMITER_RIGHT);

            while (textCompSib.hasNext()) {
                newTextComp.appendSibling(textCompSib.next());
            }

            event.setComponent(newTextComp);
        } else {
            LibMeta.LOG.warn("ColouredChat is unable to parse chat messages! Please check if another mod/plugin modifies chat.");
        }
    }
}
