package network.pxl8.colouredchat;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import network.pxl8.colouredchat.lib.LibDataStorage;

import java.util.EventListener;
import java.util.Random;

public class ChatListener implements EventListener{
    TextFormatting[] colours = {
            TextFormatting.DARK_GREEN,
            TextFormatting.DARK_AQUA,
            TextFormatting.DARK_RED,
            TextFormatting.DARK_PURPLE,
            TextFormatting.GOLD,
            TextFormatting.BLUE,
            TextFormatting.GREEN,
            TextFormatting.AQUA,
            TextFormatting.RED,
            TextFormatting.LIGHT_PURPLE,
            TextFormatting.YELLOW
    };

    public String randomColour() {
        Random rand = new Random();
        return colours[rand.nextInt(colours.length)].toString();
    }

    @SubscribeEvent
    public void test(PlayerEvent.PlayerLoggedInEvent event) {
        LibDataStorage data = LibDataStorage.get(event.player.world);
        data.addPlayerColour(event.player, randomColour());
        data.markDirty();
    }
    @SubscribeEvent
    public void test(PlayerEvent.PlayerLoggedOutEvent event) {
        LibDataStorage data = LibDataStorage.get(event.player.world);
        data.removePlayerColour(event.player);
        data.markDirty();
    }

    @SubscribeEvent
    public void onServerMsg( ServerChatEvent event) {
        LibDataStorage data = LibDataStorage.get(event.getPlayer().world);

        event.setCanceled(true);

        String orgmsg = event.getMessage();
        String player = event.getPlayer().getDisplayNameString();
        String prefix = data.getPlayerColour(event.getPlayer());
        String suffix = TextFormatting.RESET.toString();
        String newmsg = "<" + prefix + player + suffix + "> " + orgmsg;

        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(ForgeHooks.newChatWithLinks(newmsg));
    }
}
