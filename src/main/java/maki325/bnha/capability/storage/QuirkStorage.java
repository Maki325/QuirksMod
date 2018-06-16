package maki325.bnha.capability.storage;

import maki325.bnha.api.Quirk;
import maki325.bnha.capability.IQuirk;
import maki325.bnha.init.ModQuirks;
import maki325.bnha.util.Utils;
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
			tag.setTag("" + i, q.toNBT());
			i++;
		}
		
		return tag;
	}

	@Override
	public void readNBT(Capability<IQuirk> capability, IQuirk instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound tag = (NBTTagCompound) nbt;
		
		for(int i = 0;i < tag.getSize();i++) {
			NBTTagCompound q = (NBTTagCompound) tag.getTag("" + i);
			
			if(q == null) {
				break;
			}
			
			instance.addQuirks(Utils.nbtToQuirk(q));
		}
	}

}
