package network.pxl8.colouredchat.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TextFormatting;
import network.pxl8.colouredchat.api.capability.IColourData;

public class ColourData implements IColourData {
    private TextFormatting randomSetColour;
    private TextFormatting playerSetColour;
    private TextFormatting quasiRandomSetColour;

    private String teamID;

    private Boolean usePlayerColour;

    public ColourData() {
        setRandomColour(TextFormatting.WHITE);
        setPlayerColour(TextFormatting.WHITE);
        setQuasiRandomColour(TextFormatting.WHITE);
        setTeamID("");
        setUsePlayerColour(false);
    }

    @Override
    public TextFormatting getRandomColour() { return randomSetColour; }
    @Override
    public void setRandomColour(TextFormatting colour) { this.randomSetColour = colour; }
    @Override
    public TextFormatting getPlayerColour() { return playerSetColour; }
    @Override
    public void setPlayerColour(TextFormatting colour) { this.playerSetColour = colour; }
    @Override
    public TextFormatting getQuasiRandomColour() { return quasiRandomSetColour; }
    @Override
    public void setQuasiRandomColour(TextFormatting colour) { this.quasiRandomSetColour = colour; }

    @Override
    public String getTeamID() { return teamID; }
    @Override
    public void setTeamID(String id) { this.teamID = id; }

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
            tag.putString("teamID", teamID);
            tag.putBoolean("usePlayerColour", usePlayerColour);
            return tag;
    }
    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        setRandomColour(TextFormatting.fromColorIndex(nbt.getInt("randomColour")));
        setPlayerColour(TextFormatting.fromColorIndex(nbt.getInt("playerColour")));
        setQuasiRandomColour(TextFormatting.fromColorIndex(nbt.getInt("quasiRandomColour")));
        setTeamID(nbt.getString("teamID"));
        setUsePlayerColour(nbt.getBoolean("usePlayerColour"));
    }
}