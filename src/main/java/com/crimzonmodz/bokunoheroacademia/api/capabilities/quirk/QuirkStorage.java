package com.crimzonmodz.bokunoheroacademia.api.capabilities.quirk;

import com.crimzonmodz.bokunoheroacademia.api.quirk.Quirk;
import com.crimzonmodz.bokunoheroacademia.api.quirk.QuirkRegistry;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class QuirkStorage implements Capability.IStorage<IQuirk> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IQuirk> capability, IQuirk instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();

        int i = 0;
        for(Quirk q:instance.getQuirks()) {
            if(q == null) {
                continue;
            }
            tag.putString(i + "Name", q.getId().toString());
            tag.put(String.valueOf(i), q.save());
            i++;
        }
        tag.putInt("numOfQ", i);

        return tag;
    }

    @Override
    public void readNBT(Capability<IQuirk> capability, IQuirk instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;

        int numOfQ = tag.getInt("numOfQ");

        for(int i = 0;i < numOfQ;i++) {
            Quirk q = QuirkRegistry.getQuirk(tag.getString(i + "Name"));
            if(q == null) {
                continue;
            }
            q.load(tag.getCompound(String.valueOf(i)));
            instance.addQuirks(q);
        }
    }
}