package maki325.bnha.quirks;

import maki325.bnha.api.Quirk;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class QuirkExplosive extends Quirk {

	private static int cooldown = 0;
	private static int maxCooldown = 200;
	
	private static boolean aviable = true;
	
	public QuirkExplosive() {
		super("explosive");
	}

	@Override
	public void onPlayerUse(EntityPlayer player) {
		if(aviable) {
			player.world.createExplosion(player, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2f, true);
			aviable = false;
		}
	}

	@SubscribeEvent
	public static void tick(ServerTickEvent event) {
		if(!aviable) {
			cooldown++;
			if(cooldown >= maxCooldown) {
				aviable = true;
				cooldown = 0;
			}
		}
	}
	
}
