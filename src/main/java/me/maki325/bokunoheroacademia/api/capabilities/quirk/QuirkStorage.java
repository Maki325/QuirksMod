package me.maki325.bokunoheroacademia.api.capabilities.quirk;

import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import me.maki325.bokunoheroacademia.api.quirk.QuirkRegistry;
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

        System.out.println("!!!writeNBT!!!");

        int i = 0;
        for(Quirk q : instance.getQuirks()) {
            if(q == null) continue;
            CompoundNBT nbt = q.save();
            if(nbt == null) nbt = new CompoundNBT();
            nbt.putString("id", q.getId().toString());
            tag.put(String.valueOf(i), nbt);
            i++;
        }
        tag.putInt("numberOfQuirks", i);

        return tag;
    }

    @Override
    public void readNBT(Capability<IQuirk> capability, IQuirk instance, Direction side, INBT nbt) {

        System.out.println("!!!readNBT!!!");

        CompoundNBT tag = (CompoundNBT) nbt;

        int numOfQ = tag.getInt("numberOfQuirks");

        for(int i = 0;i < numOfQ;i++) {
            Quirk quirk = QuirkRegistry.get(tag.getCompound(String.valueOf(i)));
            if(quirk != null) instance.addQuirks(quirk);
        }
    }

}
