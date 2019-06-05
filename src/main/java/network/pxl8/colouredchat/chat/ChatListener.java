package network.pxl8.colouredchat.chat;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.shadowfacts.discordchat.one_twelve_two.OneTwelveTwoMod;
import network.pxl8.colouredchat.config.Configuration;
import network.pxl8.colouredchat.data.ColourData;
import network.pxl8.colouredchat.lib.LibColour;

import java.util.EventListener;

public class ChatListener implements EventListener{
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        ColourData data = ColourData.get(event.player.world);
        data.addRandomColour(event.player, LibColour.randomColour());
        data.markDirty();
    }
    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        ColourData data = ColourData.get(event.player.world);
        data.removeRandomColour(event.player);
        data.markDirty();
    }

    @SubscribeEvent
    public void onServerMsg(ServerChatEvent event) {
        ColourData data = ColourData.get(event.getPlayer().world);
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

        //DiscordChat Compat
        if (Loader.isModLoaded("discordchat")) {
            String discordmsg = OneTwelveTwoMod.discordChat.filterMCMessage(orgmsg);
            if (discordmsg != null) {
                OneTwelveTwoMod.discordChat.sendMessage(OneTwelveTwoMod.discordChat.getFormatter().fromMC(event.getPlayer().getName(), discordmsg));
            }
        }
    }
}
