package network.pxl8.colouredchat.lib;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class LibDataStorage extends WorldSavedData{
    private static final String IDENT = LibMisc.MOD_ID + "_ChatColourData";

    public LibDataStorage() {
        super(IDENT);
    }

    public static LibDataStorage get(World world) {
        MapStorage storage = world.getMapStorage();
        LibDataStorage instance = (LibDataStorage) storage.getOrLoadData(LibDataStorage.class, IDENT);

        if(instance == null) {
            instance = new LibDataStorage();
            storage.setData(IDENT, instance);
        }
        return instance;
    }

    private HashMap<UUID, String> playerColours= new HashMap<UUID, String>();
    public void addPlayerColour(EntityPlayer player, String colour){
        playerColours.put(player.getUniqueID(), colour);
    }
    public void removePlayerColour(EntityPlayer player) {
        playerColours.remove(player.getUniqueID());
    }
    public String getPlayerColour(EntityPlayer player) {
        return playerColours.get(player.getUniqueID());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound playerColourData = new NBTTagCompound();
        if (!playerColours.isEmpty()) {
            for (Map.Entry<UUID, String> entry : playerColours.entrySet()) {
                playerColourData.setString(entry.getKey().toString(), entry.getValue());
            }
        }

        compound.setTag("player_colours", playerColourData);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagCompound playerColourData = nbt.getCompoundTag("player_colours");
        for (String key : playerColourData.getKeySet()) {
            UUID uid = UUID.fromString(key);

            playerColours.put(uid, playerColourData.getString(key));
        }
    }
}
