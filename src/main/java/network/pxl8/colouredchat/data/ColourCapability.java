package network.pxl8.colouredchat.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import network.pxl8.colouredchat.api.capability.IColourData;

import javax.annotation.Nonnull;

public class ColourCapability implements ICapabilitySerializable {
    @SuppressWarnings({"SameReturnValue"})
    private static <T> T Null() {
        return null;
    }

    @CapabilityInject(IColourData.class)
    public static final Capability<IColourData> COLOUR_DATA_CAPABILITY = Null();

    private LazyOptional<IColourData> lazyOpt = LazyOptional.of(ColourData::new);

    public static void register() {
        CapabilityManager.INSTANCE.register(IColourData.class, new Capability.IStorage<IColourData>() {
            @Override
            public INBT writeNBT(final Capability<IColourData> capability, final IColourData instance, final Direction side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(final Capability<IColourData> capability, final IColourData instance, final Direction side, final INBT nbt) {
                instance.deserializeNBT((CompoundNBT) nbt);
            }
        }, () -> new ColourData());
    }

    @Override
    public INBT serializeNBT() {
        return getCapability(COLOUR_DATA_CAPABILITY).orElse(null).serializeNBT();
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        getCapability(COLOUR_DATA_CAPABILITY).orElse(null).deserializeNBT((CompoundNBT) nbt);
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        return cap == COLOUR_DATA_CAPABILITY ? lazyOpt.cast() : LazyOptional.empty();
    }
}