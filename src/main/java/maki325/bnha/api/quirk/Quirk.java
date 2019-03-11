package maki325.bnha.api.quirk;

import java.io.Serializable;

import maki325.bnha.api.ResourceIdentifier;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;

public abstract class Quirk implements Serializable {

	private ResourceIdentifier id;
	
	public static final long serialVersionUID = -6050363179836676781L;
	
	public Quirk(ResourceIdentifier id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @param name - name of the {@link Quirk}(small letters)
	 * @param modId - id of the mod
	 */
	public Quirk(String name, String modId) {
		this(new ResourceIdentifier(modId, name));
	}
	
	/**
	 * Function called every time player uses a quirk.
	 * @param player - player whose quirk should be activated
	 */
	public abstract void onPlayerUse(EntityPlayerMP player, WorldServer world);
	
	public ResourceIdentifier getId() {
		return id;
	}

	public String getName() {
		return id.getResourceDomain();
	}

	public String getModID() {
		return id.getResourcePath();
	}
	
	public boolean isSimilar(Quirk q) {
		return id.equals(q.getId());
	}
	
	public static boolean isSimilar(Quirk q1, Quirk q2) {
		return q1.getId().equals(q2.getId());
	}
	
	public NBTTagCompound save() { return new NBTTagCompound(); };
	
	public void load(NBTTagCompound tag) {};
	
	@Override
	public String toString() {
		return "Quirk - ID: " + id;
	}
	
}
