package network.pxl8.colouredchat.chat;

import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import network.pxl8.colouredchat.ColouredChat;

public class CommandClearColour {
    static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("clear")
                .requires(cs -> cs.hasPermissionLevel(0))
                .executes(ctx -> clearColour(ctx.getSource()));
    }

    private static int clearColour(CommandSource source) {
        ColouredChat.getCap(source.getEntity()).ifPresent(colourData -> colourData.setUsePlayerColour(false));
        source.sendFeedback(new StringTextComponent("Cleared set colour"), true);
        return 0;
    }
}
