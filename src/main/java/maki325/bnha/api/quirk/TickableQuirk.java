package maki325.bnha.api.quirk;

import maki325.bnha.api.ResourceIdentifier;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public abstract class TickableQuirk extends Quirk {

	public TickableQuirk(ResourceIdentifier id) {
		super(id);
	}

	public TickableQuirk(String name, String modId) {
		super(new ResourceIdentifier(modId, name));
	}

	@Override
	public abstract void onPlayerUse(EntityPlayerMP player, WorldServer world);
	
	@SubscribeEvent
	public abstract void tick(ServerTickEvent event);

}
