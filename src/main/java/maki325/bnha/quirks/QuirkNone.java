package maki325.bnha.quirks;

import maki325.bnha.api.Quirk;
import maki325.bnha.util.Reference;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class QuirkNone extends Quirk {

	public QuirkNone() {
		super("none", Reference.MOD_ID);
		
		init();
	}

	@Override
	public void onPlayerUse(EntityPlayerMP player) {}
	

	@Override
	public NBTTagCompound save() { return new NBTTagCompound(); }

	@Override
	public void load(NBTTagCompound tag) {}

	@Override
	public void tick() {}

}
