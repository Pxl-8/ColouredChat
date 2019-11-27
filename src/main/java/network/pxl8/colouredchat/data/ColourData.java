package network.pxl8.colouredchat.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import network.pxl8.colouredchat.api.capability.IColourData;
import network.pxl8.colouredchat.lib.LibMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


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
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("randomColour", randomSetColour.getColorIndex());
        tag.setInteger("playerColour", playerSetColour.getColorIndex());
        tag.setInteger("quasiRandomColour", quasiRandomSetColour.getColorIndex());
        tag.setBoolean("usePlayerColour", usePlayerColour);
        return tag;
    }
    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        setRandomColour(TextFormatting.fromColorIndex(nbt.getInteger("randomColour")));
        setPlayerColour(TextFormatting.fromColorIndex(nbt.getInteger("playerColour")));
        setQuasiRandomColour(TextFormatting.fromColorIndex(nbt.getInteger("quasiRandomColour")));
        setUsePlayerColour(nbt.getBoolean("usePlayerColour"));
    }
}
