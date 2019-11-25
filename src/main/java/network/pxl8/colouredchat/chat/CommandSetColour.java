package network.pxl8.colouredchat.chat;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import network.pxl8.colouredchat.ColouredChat;
import network.pxl8.colouredchat.config.Configuration;
import network.pxl8.colouredchat.lib.LibColour;

public class CommandSetColour {
    static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("set")
                .requires(cs -> cs.hasPermissionLevel(0))
                .then(Commands.argument("colour", ColourArgument.colourArgument())
                        .executes(ctx -> setColour(ctx.getSource(), LibColour.getColourFromName(ctx.getArgument("colour", String.class))))
                );
        }

    private static int setColour(CommandSource source, TextFormatting colour) {
        if (!Configuration.ALLOW_CUSTOM.get()) {
            source.sendErrorMessage(new StringTextComponent("Command is disabled by config").applyTextStyle(TextFormatting.RED));
            return 0;
        } else {
            ColouredChat.getCap(source.getEntity()).ifPresent(colourData -> {
                if (!colourData.getUsePlayerColour()) colourData.setUsePlayerColour(true);
                colourData.setPlayerColour(colour);

                if (Configuration.USE_CUSTOM_TEAM_ASSIGNMENT.get()) {
                    Scoreboard sb = source.getWorld().getScoreboard();
                    String playerName = source.getName();
                    String shortName = playerName.substring(0, Math.min(playerName.length(), 8));

                    ImmutableList<ScorePlayerTeam> teams = ImmutableList.copyOf(sb.getTeams());
                    teams.forEach((team -> {if (team.getName().equals(shortName + "_colchat")) sb.removeTeam(team);}));

                    ScorePlayerTeam playerTeam = sb.createTeam(shortName + "_colchat");
                    playerTeam.setColor(colourData.getPlayerColour());
                    sb.addPlayerToTeam(playerName, playerTeam);
                }
            });
            source.sendFeedback(new StringTextComponent("Set colour to ")
                    .appendSibling(new StringTextComponent(colour.getFriendlyName().toUpperCase()).applyTextStyle(colour)), true);
            return 0;
        }
    }
}