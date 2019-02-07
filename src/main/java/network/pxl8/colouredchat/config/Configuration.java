package network.pxl8.colouredchat.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import network.pxl8.colouredchat.lib.LibMeta;

@Config(modid = LibMeta.MOD_ID, name = LibMeta.MOD_ID)
public class Configuration {

    public static ChatConf chat_config = new ChatConf();
    public static ColourConf colour_config = new ColourConf();
    public static CommandConf command_config = new CommandConf();

    public static class ChatConf {
        @Config.Comment("Modify left delimiter (Default:<)")
        @Config.Name("Left Delimiter")
        public String nameDelimiterL = "<";
        @Config.Comment("Modify right delimiter (Default:> )")
        @Config.Name("Right Delimiter")
        public String nameDelimiterR = "> ";
    }
    public static class ColourConf {
        public boolean DARK_GREEN = true;
        public boolean DARK_AQUA = true;
        public boolean DARK_RED = true;
        public boolean DARK_PURPLE = true;
        public boolean GOLD = true;
        public boolean BLUE = true;
        public boolean GREEN = true;
        public boolean AQUA = true;
        public boolean RED = true;
        public boolean LIGHT_PURPLE = true;
        public boolean YELLOW = true;
    }
    public static class CommandConf {
        @Config.Comment("Allow players to customise their default colour")
        public boolean allowCustomColours = true;
    }

    @Mod.EventBusSubscriber
    public static class ConfigSync {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event){
            if (event.getModID().equals(LibMeta.MOD_ID)) { ConfigManager.sync(LibMeta.MOD_ID, Config.Type.INSTANCE); }
        }
    }

}
