package network.pxl8.colouredchat.proxy;

import net.minecraftforge.common.MinecraftForge;
import network.pxl8.colouredchat.chat.ChatListener;

public class CommonProxy implements Proxy {
    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(new ChatListener());
    }

    @Override
    public void init() {

    }

    @Override
    public void postInit() {

    }
}
