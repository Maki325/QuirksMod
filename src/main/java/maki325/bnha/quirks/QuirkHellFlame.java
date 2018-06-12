package maki325.bnha.quirks;

import com.jcraft.jorbis.Block;

import maki325.bnha.api.LevelUp;
import maki325.bnha.api.Quirk;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder.Spawn;

public class QuirkHellFlame extends Quirk {

	private static EntityPlayer p;
	
	public QuirkHellFlame() {
		super("hellflame");
		
		setMaxCooldown(2000);
		setLevelMinimum(100);
		setLevelFactor(5);
		setMaxActivatedTime(1);
		setLevelUp(new LevelUp(0, 0.99));
		setXpPerTick(1/20);
	}

	@Override
	public void onPlayerUse(EntityPlayer player) {
		p = player;
		if(aviable) {
			player.world.newExplosion(player, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), (float) (2), true, false);

			aviable = false;
			xp += level * 2.75;
		}
	}
	
	@Override
	public void onClient(WorldClient worldClient, double x, double y, double z) {
		super.onClient(worldClient, x, y, z);
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
		if(xp >= nextXp) {
			level++;
			nextXp = xp * levelFactor;
			if(p != null) {
				p.sendMessage(new TextComponentString(TextFormatting.AQUA + "Level Up"));
				p.sendMessage(new TextComponentString(TextFormatting.AQUA + "Your now level " + level));
			}
			maxCooldown *= levelUp.getCooldownMultiplier();
		}
	}
	
}
