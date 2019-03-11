package maki325.bnha.api.capabilities.quirk;

import maki325.bnha.api.BnHAUtils;
import maki325.bnha.api.quirk.Quirk;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class QuirkStorage implements IStorage<IQuirk> {

	@Override
	public NBTBase writeNBT(Capability<IQuirk> capability, IQuirk instance, EnumFacing side) {
		NBTTagCompound tag = new NBTTagCompound();
		
		int i = 0;
		for(Quirk q:instance.getQuirks()) {
			if(q == null) {
				continue;
			}
			tag.setTag("" + i, BnHAUtils.serializeQuirk(q));
			i++;
		}
		tag.setInteger("numOfQ", i);
		
		return tag;
	}

	@Override
	public void readNBT(Capability<IQuirk> capability, IQuirk instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound tag = (NBTTagCompound) nbt;
		
		int numOfQ = tag.getInteger("numOfQ");
		
		for(int i = 0;i < numOfQ;i++) {
			Quirk q = BnHAUtils.deserializeQuirk(tag.getCompoundTag("" + i));
			
			if(q == null) {
				continue;
			}
			
			instance.addQuirks(q);
		}
	}

}