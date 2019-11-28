package network.pxl8.colouredchat.api.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TextFormatting;

public interface IColourData {
    TextFormatting getRandomColour();
    void setRandomColour(TextFormatting colourData);
    TextFormatting getPlayerColour();
    void setPlayerColour(TextFormatting colourData);
    TextFormatting getQuasiRandomColour();
    void setQuasiRandomColour(TextFormatting colourData);

    String getTeamID();
    void setTeamID(String teamID);

    Boolean getUsePlayerColour();
    void setUsePlayerColour(Boolean bool);

    CompoundNBT serializeNBT();
    void deserializeNBT(CompoundNBT nbt);
}