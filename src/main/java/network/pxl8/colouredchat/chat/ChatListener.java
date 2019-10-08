package network.pxl8.colouredchat.chat;

import com.google.gson.JsonObject;
import net.minecraft.util.text.*;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.pxl8.colouredchat.ColouredChat;
import network.pxl8.colouredchat.config.Configuration;
import network.pxl8.colouredchat.lib.LibColour;

import java.util.Iterator;

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
        });
    }
    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
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