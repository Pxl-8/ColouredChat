package network.pxl8.colouredchat.chat;

import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.text.StringTextComponent;
import network.pxl8.colouredchat.ColouredChat;
import network.pxl8.colouredchat.config.Configuration;
import network.pxl8.colouredchat.lib.LibColour;

public class CommandClearColour {
    static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("clear")
                .requires(cs -> cs.hasPermissionLevel(0))
                .executes(ctx -> clearColour(ctx.getSource()));
    }

    private static int clearColour(CommandSource source) {
        ColouredChat.getCap(source.getEntity()).ifPresent(colourData -> {
            colourData.setUsePlayerColour(false);

            if (Configuration.USE_CUSTOM_TEAM_ASSIGNMENT.get()) {
                Scoreboard sb = source.getWorld().getScoreboard();
                String playerName = source.getName();
                LibColour.clearTeam(sb, source.getEntity());

                ScorePlayerTeam playerTeam = sb.createTeam(colourData.getTeamID());
                playerTeam.setColor(colourData.getRandomColour());
                sb.addPlayerToTeam(playerName, playerTeam);
            }
        });

        source.sendFeedback(new StringTextComponent("Cleared set colour"), true);
        return 0;
    }
}
