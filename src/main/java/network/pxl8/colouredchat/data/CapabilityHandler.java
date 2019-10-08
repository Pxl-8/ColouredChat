package network.pxl8.colouredchat.data;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.pxl8.colouredchat.ColouredChat;
import network.pxl8.colouredchat.lib.LibMeta;

@Mod.EventBusSubscriber
public class CapabilityHandler {
    private static final ResourceLocation COLOUR_CAP = new ResourceLocation(LibMeta.MOD_ID, "colour_data");

    @SubscribeEvent
    public static void attachCap(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(COLOUR_CAP, new ColourCapability());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        PlayerEntity oldPlayer = event.getOriginal();
        PlayerEntity newPlayer = event.getPlayer();

        ColouredChat.getCap(oldPlayer).ifPresent(colourDataOld -> {
            ColouredChat.getCap(newPlayer).ifPresent(colourDataNew -> {
                colourDataNew.setPlayerColour(colourDataOld.getPlayerColour());
                colourDataNew.setRandomColour(colourDataOld.getRandomColour());
                colourDataNew.setQuasiRandomColour(colourDataOld.getQuasiRandomColour());
                colourDataNew.setUsePlayerColour(colourDataOld.getUsePlayerColour());
            });
        });

    }
}