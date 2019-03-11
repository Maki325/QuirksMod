package maki325.bnha.api.functional;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@FunctionalInterface
public interface Function extends Serializable {
	
	public static final long serialVersionUID = -8980736712587678371L;

	void accept(NBTTagCompound data, World world, BlockPos pos, EntityPlayer player);

}
