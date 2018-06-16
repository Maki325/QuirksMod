package maki325.bnha.capability.providers;

import maki325.bnha.capability.IQuirk;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class QuirkProvider implements ICapabilitySerializable<NBTBase> {

	@CapabilityInject(IQuirk.class) 
	public static final Capability<IQuirk> QUIRK_CAP = null; 

	private IQuirk instance = QUIRK_CAP.getDefaultInstance(); 
	
	@Override 
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) { 
		return capability == QUIRK_CAP; 
	} 

	@Override 
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) { 
		return capability == QUIRK_CAP ? QUIRK_CAP.<T> cast(this.instance) : null; 
	} 

	@Override 
	public NBTBase serializeNBT() { 
		return QUIRK_CAP.getStorage().writeNBT(QUIRK_CAP, this.instance, null); 
	} 

	@Override 
	public void deserializeNBT(NBTBase nbt) { 
		QUIRK_CAP.getStorage().readNBT(QUIRK_CAP, this.instance, null, nbt); 
	} 

}
