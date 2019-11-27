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
    public static Experimental experimental = new Experimental();

    public static class ChatConf {
        @Config.Comment("Modify left delimiter [Default: \"<\"]")
        public String DELIMITER_LEFT = "<";
        @Config.Comment("Modify right delimiter [Default: \"> \"]")
        public String DELIMITER_RIGHT = "> ";
    }
    public static class ColourConf {
        @Config.Comment("Allowed colours for random and custom assignment")
        public boolean AQUA = true;
        public boolean BLUE = true;
        public boolean DARK_AQUA = true;
        public boolean DARK_GREEN = true;
        public boolean DARK_PURPLE = true;
        public boolean DARK_RED = true;
        public boolean GOLD = true;
        public boolean GREEN = true;
        public boolean LIGHT_PURPLE = true;
        public boolean RED = true;
        public boolean YELLOW = true;
    }
    public static class CommandConf {
        @Config.Comment("Allow the use of the /colouredchat set|clear <colour> command, This command allows players to customise their player chat colour, persists on relogs")
        public boolean ALLOW_CUSTOM = true;
        @Config.Comment("Allow the use of the /colouredchat random command, This command allows players to randomly select a player chat colour, does not persist on relog")
        public boolean ALLOW_RANDOM = true;
    }
    public static class Experimental {
        @Config.Comment("Enable experimental quasi-random colour assignment, This experimental mode will evenly distribute colours to players on login, reducing duplicate colours")
        public boolean USE_QUASI_RANDOM_ASSIGNMENT = false;
        //@Config.Comment("Enable assigning players to a team with their colour, This will allow other players to see your name colour in the player list and in the floating name above your player")
        //public boolean USE_CUSTOM_TEAM_ASSIGNMENT = false;
    }

    @Mod.EventBusSubscriber
    public static class ConfigSync {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event){
            if (event.getModID().equals(LibMeta.MOD_ID)) { ConfigManager.sync(LibMeta.MOD_ID, Config.Type.INSTANCE); }
        }
    }
}
