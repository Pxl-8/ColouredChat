package network.pxl8.colouredchat.chat;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.ISuggestionProvider;
import network.pxl8.colouredchat.lib.LibColour;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ColourArgument implements ArgumentType<String> {
    //private static SimpleCommandExceptionType UNKNOWN_COLOUR = new SimpleCommandExceptionType(new StringTextComponent("Invalid colour input"));

    public static ColourArgument colourArgument() {
        return new ColourArgument();
    }

    @Override
    public String parse(final StringReader reader) throws CommandSyntaxException {
        // Currently broken TODO:Fix command exception for colour argument
        //if (!ColouredChat.COLOURS.contains(LibColour.getColourFromName(reader.readString()))) { throw UNKNOWN_COLOUR.create(); }
        return reader.readString().toUpperCase();
    }

    @Override
    public <S>CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        List<String> coloursFriendly = new ArrayList<>();
        LibColour.getColours().forEach(colour -> {
            coloursFriendly.add((colour.getFriendlyName()).toUpperCase());
        });

        return ISuggestionProvider.suggest(coloursFriendly, builder);
    }
}