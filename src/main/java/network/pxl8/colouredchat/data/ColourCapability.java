package network.pxl8.colouredchat.data;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.*;
import network.pxl8.colouredchat.api.capability.IColourData;

import javax.annotation.Nullable;

public class ColourCapability implements ICapabilitySerializable {
    @CapabilityInject(IColourData.class)
    public static final Capability<IColourData> COLOUR_DATA_CAPABILITY = null;
    private IColourData INST = COLOUR_DATA_CAPABILITY.getDefaultInstance();

    private static final EnumFacing FACING = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IColourData.class, new Capability.IStorage<IColourData>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IColourData> capability, IColourData instance, EnumFacing side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IColourData> capability, IColourData instance, EnumFacing side, NBTBase nbt) {
                instance.deserializeNBT((NBTTagCompound) nbt);
            }
        }, () -> new ColourData());
    }

    @Override
    public NBTBase serializeNBT() {
        return getCapability(COLOUR_DATA_CAPABILITY, FACING).serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        getCapability(COLOUR_DATA_CAPABILITY, FACING).deserializeNBT((NBTTagCompound) nbt);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == COLOUR_DATA_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == COLOUR_DATA_CAPABILITY ? COLOUR_DATA_CAPABILITY.cast(this.INST) : null;
    }
}
