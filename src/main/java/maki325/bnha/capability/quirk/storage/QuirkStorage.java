package maki325.bnha.capability.quirk.storage;

import maki325.bnha.api.Quirk;
import maki325.bnha.capability.quirk.IQuirk;
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
			if(q == null) {
				System.out.println("QUIRK IS NULL");
				continue;
			}
			tag.setTag("" + i, q.toNBT());
			i++;
		}
		tag.setInteger("numOfQ", i);
		
		tag.setInteger("points", instance.getPoints());
		
		return tag;
	}

	@Override
	public void readNBT(Capability<IQuirk> capability, IQuirk instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound tag = (NBTTagCompound) nbt;
		
		int numOfQ = tag.getInteger("numOfQ");
		if(numOfQ == 0) {
			numOfQ = tag.getSize();
		}
		
		for(int i = 0;i < numOfQ;i++) {
			//NBTTagCompound q = (NBTTagCompound) tag.getTag("" + i);
			NBTTagCompound q = tag.getCompoundTag("" + i);
			
			if(q == null) {
				continue;
			}
			
			instance.addQuirks(Quirk.nbtToQuirk(q));
		}
		
		instance.addPoints(tag.getInteger("points"));
	}

}
