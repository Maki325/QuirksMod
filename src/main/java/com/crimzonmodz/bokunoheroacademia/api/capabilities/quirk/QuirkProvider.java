package com.crimzonmodz.bokunoheroacademia.api.capabilities.quirk;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class QuirkProvider implements ICapabilitySerializable<INBT> {

    @CapabilityInject(IQuirk.class)
    public static final Capability<IQuirk> QUIRK_CAP = null;

    private NonNullSupplier<IQuirk> nonNullSupplier = new NonNullSupplier<IQuirk>() {
        @Nonnull
        @Override
        public IQuirk get() {
            return null;
        }
    };

    private final LazyOptional<IQuirk> holder = LazyOptional.of(CapabilityQuirk::new);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == QUIRK_CAP ? holder.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return QUIRK_CAP.getStorage().writeNBT(QUIRK_CAP, holder.orElseGet(nonNullSupplier), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        QUIRK_CAP.getStorage().readNBT(QUIRK_CAP, holder.orElseGet(nonNullSupplier), null, nbt);
    }

}
