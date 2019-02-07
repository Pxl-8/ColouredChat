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
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
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
        colours.add("DARK_GREEN");
        colours.add("DARK_AQUA");
        colours.add("DARK_RED");
        colours.add("DARK_PURPLE");
        colours.add("GOLD");
        colours.add("BLUE");
        colours.add("GREEN");
        colours.add("AQUA");
        colours.add("RED");
        colours.add("LIGHT_PURPLE");
        colours.add("YELLOW");
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
        return "colouredchat clear|set <colour>";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) throw new WrongUsageException(getUsage(sender));

        String cmd = args[0].toLowerCase(Locale.ENGLISH);
        if (cmd.equals("set")) {
            if (!Configuration.command_config.allowCustomColours) throw new CommandException("Command is disabled");
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
            ColourData data = ColourData.get(sender.getEntityWorld());

            if (sender instanceof EntityPlayer) {
                data.addDefaultColour((EntityPlayerMP) sender, LibColour.getColourFromName(colour));
            }

            data.markDirty();
            sender.sendMessage(new TextComponentString(TextFormatting.GRAY + "Set default name colour to " + LibColour.getColourFromName(colour) + colour));
        } else if (cmd.equals("clear")) {
            if (!Configuration.command_config.allowCustomColours) throw new CommandException("Command is disabled");
            ColourData data = ColourData.get(sender.getEntityWorld());

            if (sender instanceof EntityPlayer) {
                data.removeDefaultColour((EntityPlayerMP) sender);
            }

            data.markDirty();
            sender.sendMessage(new TextComponentString(TextFormatting.GRAY + "Cleared custom name colour"));
        } else { throw new WrongUsageException(getUsage(sender)); }

    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetpos) {
        Boolean cmd_set = args[0].equalsIgnoreCase("set");

        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, Lists.newArrayList("clear", "set"));
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
