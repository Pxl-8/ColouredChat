package network.pxl8.colouredchat;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import network.pxl8.colouredchat.chat.ChatCommand;
import network.pxl8.colouredchat.lib.LibColour;
import network.pxl8.colouredchat.lib.LibMeta;
import network.pxl8.colouredchat.proxy.Proxy;

import java.util.List;


@Mod(modid = LibMeta.MOD_ID, version = LibMeta.VERSION, acceptableRemoteVersions = "*")
public class ColouredChat {
    @SidedProxy(clientSide = LibMeta.CLIENT_PROXY, serverSide = LibMeta.SERVER_PROXY, modId = LibMeta.MOD_ID)
    public static Proxy proxy;

    @Mod.Instance
    public static ColouredChat instance;

    public static List<TextFormatting> COLOURS = LibColour.getColours();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();
    }

    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new ChatCommand());
    }
}
