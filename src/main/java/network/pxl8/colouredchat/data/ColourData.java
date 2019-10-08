package network.pxl8.colouredchat.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TextFormatting;
import network.pxl8.colouredchat.api.capability.IColourData;

public class ColourData implements IColourData {
    private TextFormatting randomSetColour;
    private TextFormatting playerSetColour;
    private TextFormatting quasiRandomSetColour;

    private Boolean usePlayerColour;

    public ColourData() {
        setRandomColour(TextFormatting.WHITE);
        setPlayerColour(TextFormatting.WHITE);
        setQuasiRandomColour(TextFormatting.WHITE);
        setUsePlayerColour(false);
    }

    @Override
    public TextFormatting getRandomColour() { return randomSetColour; }
    @Override
    public void setRandomColour(final TextFormatting colour) { this.randomSetColour = colour; }
    @Override
    public TextFormatting getPlayerColour() { return playerSetColour; }
    @Override
    public void setPlayerColour(final TextFormatting colour) { this.playerSetColour = colour; }
    @Override
    public TextFormatting getQuasiRandomColour() { return quasiRandomSetColour; }
    @Override
    public void setQuasiRandomColour(final TextFormatting colour) { this.quasiRandomSetColour = colour; }

    @Override
    public Boolean getUsePlayerColour() { return usePlayerColour; }
    @Override
    public void setUsePlayerColour(Boolean bool) { this.usePlayerColour = bool; }

    @Override
    public CompoundNBT serializeNBT() {
            CompoundNBT tag = new CompoundNBT();
            tag.putInt("randomColour", randomSetColour.getColorIndex());
            tag.putInt("playerColour", playerSetColour.getColorIndex());
            tag.putInt("quasiRandomColour", quasiRandomSetColour.getColorIndex());
            tag.putBoolean("usePlayerColour", usePlayerColour);
            return tag;
    }
    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        setRandomColour(TextFormatting.fromColorIndex(nbt.getInt("randomColour")));
        setPlayerColour(TextFormatting.fromColorIndex(nbt.getInt("playerColour")));
        setQuasiRandomColour(TextFormatting.fromColorIndex(nbt.getInt("quasiRandomColour")));
        setUsePlayerColour(nbt.getBoolean("usePlayerColour"));
    }
}