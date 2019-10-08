package network.pxl8.colouredchat;

import net.minecraft.command.arguments.ArgumentSerializer;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import network.pxl8.colouredchat.api.capability.IColourData;
import network.pxl8.colouredchat.chat.ChatCommand;
import network.pxl8.colouredchat.chat.ColourArgument;
import network.pxl8.colouredchat.config.Configuration;
import network.pxl8.colouredchat.data.ColourCapability;
import network.pxl8.colouredchat.lib.LibColour;

import java.util.HashMap;
import java.util.List;

@Mod("colouredchat")
public class ColouredChat {
    public static List<TextFormatting> COLOURS = LibColour.getColours();
    public static TextFormatting LAST_COLOUR = TextFormatting.WHITE;
    public static HashMap<TextFormatting, Integer> COLOUR_MAP;
    static {
        COLOUR_MAP = new HashMap<>();
        ColouredChat.COLOURS.forEach(colour -> COLOUR_MAP.put(colour, 0));
    }

    public static LazyOptional<IColourData> getCap(Entity player) {
        return player.getCapability(ColourCapability.COLOUR_DATA_CAPABILITY);
    }

    public ColouredChat() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.COMMON_CONFIG);
        Configuration.loadConfig(Configuration.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("colouredchat-common.toml"));
    }

    private void setup(final FMLCommonSetupEvent event) {
        ArgumentTypes.register("colouredchat:colour", ColourArgument.class, new ArgumentSerializer<>(ColourArgument::colourArgument));
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        new ChatCommand(event.getCommandDispatcher());
    }
}