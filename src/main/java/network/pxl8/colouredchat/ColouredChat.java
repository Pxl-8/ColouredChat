package network.pxl8.colouredchat;

import net.minecraft.entity.Entity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import network.pxl8.colouredchat.api.capability.IColourData;
import network.pxl8.colouredchat.chat.ChatCommand;
import network.pxl8.colouredchat.data.ColourCapability;
import network.pxl8.colouredchat.lib.LibColour;
import network.pxl8.colouredchat.lib.LibMeta;
import network.pxl8.colouredchat.proxy.Proxy;

import java.util.HashMap;
import java.util.List;


@Mod(modid = LibMeta.MOD_ID, version = LibMeta.VERSION, acceptableRemoteVersions = "*")
public class ColouredChat {
    @Mod.Instance
    public static ColouredChat instance;

    public static List<TextFormatting> COLOURS;
    public static TextFormatting LAST_COLOUR;
    public static HashMap<TextFormatting, Integer> COLOUR_MAP = new HashMap<>();

    public static IColourData getCap(Entity player) {
        return player.getCapability(ColourCapability.COLOUR_DATA_CAPABILITY, null);
    }
    public static boolean hasCap(Entity player) {
        return player.hasCapability(ColourCapability.COLOUR_DATA_CAPABILITY, null);
    }

    @SidedProxy(clientSide = LibMeta.CLIENT_PROXY, serverSide = LibMeta.SERVER_PROXY, modId = LibMeta.MOD_ID)
    private static Proxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) { proxy.preInit(); }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) { proxy.init(); }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) { proxy.postInit(); }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        COLOURS = LibColour.getColours();
        ColouredChat.COLOURS.forEach(colour -> COLOUR_MAP.put(colour, 0));
        LAST_COLOUR = TextFormatting.WHITE;

        event.registerServerCommand(new ChatCommand());
    }
}
