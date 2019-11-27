package network.pxl8.colouredchat.data;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import network.pxl8.colouredchat.ColouredChat;
import network.pxl8.colouredchat.lib.LibMeta;

@Mod.EventBusSubscriber
public class CapabilityHandler {
    public static final ResourceLocation COLOUR_CAP = new ResourceLocation(LibMeta.MOD_ID, "colour_data");

    @SubscribeEvent
    public static void attachCap(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(COLOUR_CAP, new ColourCapability());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        EntityPlayer oldPlayer = event.getOriginal();
        EntityPlayer newPlayer = event.getEntityPlayer();

        if (ColouredChat.hasCap(oldPlayer) && ColouredChat.hasCap(newPlayer)) {
            ColouredChat.getCap(newPlayer).setPlayerColour(ColouredChat.getCap(oldPlayer).getPlayerColour());
            ColouredChat.getCap(newPlayer).setRandomColour(ColouredChat.getCap(oldPlayer).getRandomColour());
            ColouredChat.getCap(newPlayer).setQuasiRandomColour(ColouredChat.getCap(oldPlayer).getQuasiRandomColour());
            ColouredChat.getCap(newPlayer).setUsePlayerColour(ColouredChat.getCap(oldPlayer).getUsePlayerColour());
        }
    }
}
