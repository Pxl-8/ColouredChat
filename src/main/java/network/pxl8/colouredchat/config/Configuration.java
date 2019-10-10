package network.pxl8.colouredchat.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Configuration {

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.ConfigValue<String> DELIMITER_LEFT;
    public static ForgeConfigSpec.ConfigValue<String> DELIMITER_RIGHT;

    public static ForgeConfigSpec.BooleanValue DARK_GREEN;
    public static ForgeConfigSpec.BooleanValue DARK_AQUA;
    public static ForgeConfigSpec.BooleanValue DARK_RED;
    public static ForgeConfigSpec.BooleanValue DARK_PURPLE;
    public static ForgeConfigSpec.BooleanValue GOLD;
    public static ForgeConfigSpec.BooleanValue BLUE;
    public static ForgeConfigSpec.BooleanValue GREEN;
    public static ForgeConfigSpec.BooleanValue AQUA;
    public static ForgeConfigSpec.BooleanValue RED;
    public static ForgeConfigSpec.BooleanValue LIGHT_PURPLE;
    public static ForgeConfigSpec.BooleanValue YELLOW;

    public static ForgeConfigSpec.BooleanValue ALLOW_CUSTOM;
    public static ForgeConfigSpec.BooleanValue ALLOW_RANDOM;

    public static ForgeConfigSpec.BooleanValue USE_QUASI_RANDOM_ASSIGNMENT;

    static {
        COMMON_BUILDER.push("chat_config");
        setupChatConfig();
        COMMON_BUILDER.push("colour_config");
        setupColourConfig();
        COMMON_BUILDER.push("command_config");
        setupCommandConfig();
        COMMON_BUILDER.push("experimental");
        setupExperimental();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    private static void setupChatConfig() {
        DELIMITER_LEFT  = COMMON_BUILDER.comment("Left Delimiter", "[Default: \"<\"]")
                .define("DELIMITER_LEFT", "<");
        DELIMITER_RIGHT = COMMON_BUILDER.comment("Right Delimiter", "[Default: \"> \"]")
                .define("DELIMITER_RIGHT", "> ");
        COMMON_BUILDER.pop();
    }
    private static void setupColourConfig() {
        COMMON_BUILDER.comment("Allowed colours for random and custom assignment", "! - Requires Restart").push("colours");
        DARK_GREEN      = COMMON_BUILDER.define("DARK_GREEN", true);
        DARK_AQUA       = COMMON_BUILDER.define("DARK_AQUA", true);
        DARK_RED        = COMMON_BUILDER.define("DARK_RED", true);
        DARK_PURPLE     = COMMON_BUILDER.define("DARK_PURPLE", true);
        GOLD            = COMMON_BUILDER.define("GOLD", true);
        BLUE            = COMMON_BUILDER.define("BLUE", true);
        GREEN           = COMMON_BUILDER.define("GREEN", true);
        AQUA            = COMMON_BUILDER.define("AQUA", true);
        RED             = COMMON_BUILDER.define("RED", true);
        LIGHT_PURPLE    = COMMON_BUILDER.define("LIGHT_PURPLE", true);
        YELLOW          = COMMON_BUILDER.define("YELLOW", true);
        COMMON_BUILDER.pop(2);
    }
    private static void setupCommandConfig() {
        ALLOW_CUSTOM = COMMON_BUILDER.comment("Allow the use of the /colouredchat set|clear <colour> command", "This command allows players to customise their player chat colour, persists on relogs")
                .define("ALLOW_CUSTOM", true);
        ALLOW_RANDOM = COMMON_BUILDER.comment("Allow the use of the /colouredchat random command", "This command allows players to randomly select a player chat colour, does not persist on relog")
                .define("ALLOW_RANDOM", true);
        COMMON_BUILDER.pop();
    }

    private static void setupExperimental() {
        USE_QUASI_RANDOM_ASSIGNMENT = COMMON_BUILDER.comment("Enable experimental quasi-random colour assignment", "This experimental mode will evenly distribute colours to players on login, reducing duplicate colours")
                .define("USE_QUASI_RANDOM_ASSIGNMENT", true);
        COMMON_BUILDER.pop();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        configData.load();
        spec.setConfig(configData);
    }

}
