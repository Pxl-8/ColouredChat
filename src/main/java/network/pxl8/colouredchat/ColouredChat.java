package network.pxl8.colouredchat;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import network.pxl8.colouredchat.lib.LibMisc;
import network.pxl8.colouredchat.proxy.Proxy;
import org.apache.logging.log4j.Logger;


@Mod(modid = LibMisc.MOD_ID, version = LibMisc.VERSION, acceptableRemoteVersions = "*")
public class ColouredChat {
    @SidedProxy(clientSide = "network.pxl8.colouredchat.proxy.ClientProxy", serverSide = "network.pxl8.colouredchat.proxy.CommonProxy", modId = LibMisc.MOD_ID)
    public static Proxy proxy;

    @Mod.Instance
    public static ColouredChat instance;

    public static Logger log;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();
        log = event.getModLog();
    }

    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }
}
