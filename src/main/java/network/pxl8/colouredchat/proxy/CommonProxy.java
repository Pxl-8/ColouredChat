package network.pxl8.colouredchat.proxy;

import net.minecraftforge.common.MinecraftForge;
import network.pxl8.colouredchat.chat.ChatListener;
import network.pxl8.colouredchat.data.CapabilityHandler;
import network.pxl8.colouredchat.data.ColourCapability;

public class CommonProxy implements Proxy {
    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(new ChatListener());
        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
        ColourCapability.register();
    }

    @Override
    public void init() {}

    @Override
    public void postInit() {}
}
