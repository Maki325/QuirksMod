package me.maki325.bokunoheroacademia.api.capabilities.quirk;

import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import me.maki325.bokunoheroacademia.api.quirk.QuirkRegistry;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class QuirkStorage implements Capability.IStorage<IQuirk> {

    @Override
    public NBTBase writeNBT(Capability<IQuirk> capability, IQuirk instance, EnumFacing side) {
        NBTTagCompound tag = new NBTTagCompound();

        int i = 0;
        for(Quirk q : instance.getQuirks()) {
            if(q == null) continue;
            NBTTagCompound nbt = q.save();
            if(nbt == null) nbt = new NBTTagCompound();
            nbt.setString("id", q.getId().toString());
            tag.setTag(String.valueOf(i), nbt);
            i++;
        }
        tag.setInteger("numberOfQuirks", i);

        return tag;
    }

    @Override
    public void readNBT(Capability<IQuirk> capability, IQuirk instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound tag = (NBTTagCompound) nbt;

        int numOfQ = tag.getInteger("numberOfQuirks");

        for(int i = 0;i < numOfQ;i++) {
            Quirk quirk = QuirkRegistry.get((NBTTagCompound) tag.getTag(String.valueOf(i)));
            if(quirk != null) instance.addQuirks(quirk);
        }
    }

}
