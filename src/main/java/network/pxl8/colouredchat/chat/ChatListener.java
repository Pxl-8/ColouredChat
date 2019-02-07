package network.pxl8.colouredchat.chat;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import network.pxl8.colouredchat.config.Configuration;
import network.pxl8.colouredchat.data.colourData;
import network.pxl8.colouredchat.lib.LibColour;

import java.util.EventListener;

public class ChatListener implements EventListener{
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        colourData data = colourData.get(event.player.world);
        data.addRandomColour(event.player, LibColour.randomColour());
        data.markDirty();
    }
    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        colourData data = colourData.get(event.player.world);
        data.removeRandomColour(event.player);
        data.markDirty();
    }

    @SubscribeEvent
    public void onServerMsg( ServerChatEvent event) {
        colourData data = colourData.get(event.getPlayer().world);
        event.setCanceled(true);

        String delimL = Configuration.chat_config.nameDelimiterL;
        String prefix = data.getRandomColour(event.getPlayer());
        if (data.getDefaultColour(event.getPlayer()) != null) {
           prefix = data.getDefaultColour(event.getPlayer());
        }
        String player = event.getPlayer().getDisplayNameString();
        String suffix = TextFormatting.RESET.toString();
        String delimR = Configuration.chat_config.nameDelimiterR;

        String orgmsg = event.getMessage();
        String newmsg = delimL + prefix + player + suffix + delimR + orgmsg;

        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(ForgeHooks.newChatWithLinks(newmsg));
    }
}
