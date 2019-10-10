package network.pxl8.colouredchat.chat;

import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import network.pxl8.colouredchat.ColouredChat;
import network.pxl8.colouredchat.config.Configuration;

public class CommandListColour {
    static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("list")
                .requires(cs -> cs.hasPermissionLevel(0))
                .executes(ctx -> listColours(ctx.getSource()));
    }

    private static int listColours(CommandSource source) {
        TextComponent colours = new StringTextComponent("Available colours: \n");

        if (!Configuration.ALLOW_CUSTOM.get()) {
            source.sendErrorMessage(new StringTextComponent("Command is disabled by config").applyTextStyle(TextFormatting.RED));
            return 0;
        } else {
            ColouredChat.COLOURS.forEach(colour -> {
                colours.appendSibling
                        ((new StringTextComponent(colour.getFriendlyName().toUpperCase()))
                                .setStyle(new Style().setColor(colour).setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/colouredchat set " + colour.getFriendlyName().toUpperCase())))
                        );
                colours.appendSibling(new StringTextComponent(", "));
            });
            source.sendFeedback(colours, true);
            return 0;
        }
    }
}
