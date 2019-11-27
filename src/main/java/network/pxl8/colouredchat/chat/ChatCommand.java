package network.pxl8.colouredchat.chat;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import network.pxl8.colouredchat.ColouredChat;
import network.pxl8.colouredchat.config.Configuration;
import network.pxl8.colouredchat.data.ColourData;
import network.pxl8.colouredchat.lib.LibColour;
import network.pxl8.colouredchat.lib.LibMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ChatCommand extends CommandBase{
    public ChatCommand() {
        aliases = new ArrayList<>();
        aliases.add(LibMeta.MOD_ID);
        aliases.add("colchat");
        colours = new ArrayList<>();
        ColouredChat.COLOURS.forEach(colour -> {
            colours.add(colour.getFriendlyName().toUpperCase());
        });
    }

    private final List<String> aliases;
    private final List<String> colours;

    @Override
    @Nonnull
    public String getName() {
        return "colouredchat";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "colouredchat random|list|clear|set <colour>";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        if (args.length < 1) throw new WrongUsageException(getUsage(sender));

        String cmd = args[0].toLowerCase(Locale.ENGLISH);
        if (cmd.equals("set")) {
            if (!Configuration.command_config.ALLOW_CUSTOM) throw new CommandException("Command is disabled by config");
            if (args.length < 2) throw new WrongUsageException("Please specify <colour>");

            String colour = args[1].toUpperCase(Locale.ENGLISH);
            switch (colour) {
                case ("DARK_GREEN"): break;
                case ("DARK_AQUA"): break;
                case ("DARK_RED"): break;
                case ("DARK_PURPLE"): break;
                case ("GOLD"): break;
                case ("BLUE"): break;
                case ("GREEN"): break;
                case ("AQUA"): break;
                case ("RED"): break;
                case ("LIGHT_PURPLE"): break;
                case ("YELLOW"): break;
                default : throw new WrongUsageException("Not a valid colour option");
            }
            if (LibColour.getColourFromName(colour) == null) throw new WrongUsageException("Colour returned null");
            if (!ColouredChat.COLOURS.contains(LibColour.getColourFromName(colour))) throw new WrongUsageException("Colour is not enabled");

            if (ColouredChat.hasCap(player)) {
                ColouredChat.getCap(player).setPlayerColour(LibColour.getColourFromName(colour));
                ColouredChat.getCap(player).setUsePlayerColour(true);

                sender.sendMessage(new TextComponentString(TextFormatting.GRAY + "Set colour to " + LibColour.getColourFromName(colour) + colour.toUpperCase()));
            }
        } else if (cmd.equals("clear")) {
            //if (!Configuration.command_config.ALLOW_CUSTOM) throw new CommandException("Command is disabled by config");

            if (ColouredChat.hasCap(player)) {
                ColouredChat.getCap(player).setUsePlayerColour(false);

                sender.sendMessage(new TextComponentString(TextFormatting.GRAY + "Cleared custom name colour"));
            }
        } else if (cmd.equals("random")) {
            if (!Configuration.command_config.ALLOW_RANDOM) throw new CommandException("Command is disabled by config");

            if (ColouredChat.hasCap(player)) {
                ColouredChat.getCap(player).setUsePlayerColour(false);

                TextFormatting colour = LibColour.randomFormattedColour();
                ColouredChat.getCap(player).setRandomColour(colour);

                sender.sendMessage(new TextComponentString(TextFormatting.GRAY + "Randomized colour to " + colour + colour.getFriendlyName().toUpperCase()));
            }
        } else if (cmd.equals("list")) {
            if (!Configuration.command_config.ALLOW_CUSTOM) throw new CommandException("Command is disabled by config");

            ITextComponent colours = new TextComponentString("Available colours: \n");
            ColouredChat.COLOURS.forEach(colour -> {
                ITextComponent text = new TextComponentString(colour.getFriendlyName().toUpperCase())
                        .setStyle(new Style().setColor(colour).setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/colouredchat set " + colour.getFriendlyName().toUpperCase())));
                colours.appendSibling(text);
                colours.appendSibling(new TextComponentString(", "));
            });

            sender.sendMessage(colours);
        } else { throw new WrongUsageException(getUsage(sender)); }

    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetpos) {
        boolean cmd_set = args[0].equalsIgnoreCase("set");

        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, Lists.newArrayList("random", "list", "clear", "set"));
        } else if (args.length == 2 && cmd_set) {
            return getListOfStringsMatchingLastWord(args, colours);
        }

        return Collections.emptyList();
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}
