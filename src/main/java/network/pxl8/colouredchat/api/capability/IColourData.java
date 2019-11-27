package network.pxl8.colouredchat.api.capability;

import net.minecraft.util.text.TextFormatting;
import net.minecraft.nbt.NBTTagCompound;

public interface IColourData {
    TextFormatting getRandomColour();
    void setRandomColour(TextFormatting colourData);
    TextFormatting getPlayerColour();
    void setPlayerColour(TextFormatting colourData);
    TextFormatting getQuasiRandomColour();
    void setQuasiRandomColour(TextFormatting colourData);

    Boolean getUsePlayerColour();
    void setUsePlayerColour(Boolean bool);

    NBTTagCompound serializeNBT();
    void deserializeNBT(NBTTagCompound nbt);
}
