package network.pxl8.colouredchat.chat;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;

public class ChatCommand{
    public ChatCommand(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSource>literal("colouredchat")
                    .then(CommandSetColour.register())
                    .then(CommandClearColour.register())
                    .then(CommandRandomColour.register())
        );
    }
}