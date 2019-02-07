package network.pxl8.colouredchat.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import network.pxl8.colouredchat.lib.LibMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class colourData extends WorldSavedData{
    private static final String IDENT = LibMeta.MOD_ID + "_playerdataDef";

    public colourData() {
        super(IDENT);
    }

    public static colourData get(World world) {
        colourData instance = (colourData) world.getMapStorage().getOrLoadData(colourData.class, IDENT);

        if (instance == null) {
            instance = new colourData();
            world.getMapStorage().setData(IDENT, instance);
        }
        return instance;
    }

    private HashMap<UUID, String> defaultColours= new HashMap<UUID, String>();
    public void addDefaultColour(EntityPlayer player, String colour){
        defaultColours.put(player.getUniqueID(), colour);
    }
    public void removeDefaultColour(EntityPlayer player) {
        defaultColours.remove(player.getUniqueID());
    }
    public String getDefaultColour(EntityPlayer player) {
        return defaultColours.get(player.getUniqueID());
    }

    private HashMap<UUID, String> randomColours= new HashMap<UUID, String>();
    public void addRandomColour(EntityPlayer player, String colour){
        randomColours.put(player.getUniqueID(), colour);
    }
    public void removeRandomColour(EntityPlayer player) {
        randomColours.remove(player.getUniqueID());
    }
    public String getRandomColour(EntityPlayer player) {
        return randomColours.get(player.getUniqueID());

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound defaultColourData = new NBTTagCompound();
        if (!defaultColours.isEmpty()) {
            for (Map.Entry<UUID, String> entry : defaultColours.entrySet()) {
                defaultColourData.setString(entry.getKey().toString(), entry.getValue());
            }
        }
        NBTTagCompound randomColourData = new NBTTagCompound();
        if (!randomColours.isEmpty()) {
            for (Map.Entry<UUID, String> entry : randomColours.entrySet()) {
                randomColourData.setString(entry.getKey().toString(), entry.getValue());
            }
        }

        compound.setTag("default_colours", defaultColourData);
        compound.setTag("random_colours", randomColourData);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagCompound defaultColourData = nbt.getCompoundTag("default_colours");
        for (String key : defaultColourData.getKeySet()) {
            UUID uid = UUID.fromString(key);
            defaultColours.put(uid, defaultColourData.getString(key));
        }
        NBTTagCompound randomColourData = nbt.getCompoundTag("random_colours");
        for (String key : randomColourData.getKeySet()) {
            UUID uid = UUID.fromString(key);
            randomColours.put(uid, randomColourData.getString(key));
        }
    }
}
