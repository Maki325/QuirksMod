package maki325.bnha.quirks;

import maki325.bnha.api.LevelUp;
import maki325.bnha.api.Quirk;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class QuirkExplosive extends Quirk {
	
	private static EntityPlayer p;
	
	public QuirkExplosive() {
		super("explosive");
		setMaxCooldown(2000);
		setLevelMinimum(100);
		setLevelFactor(5);
		setMaxActivatedTime(1);
		setLevelUp(new LevelUp(0, 0.99));
		setXpPerTick(1/20);
		
		init();
	}

	@Override
	public void onPlayerUse(EntityPlayer player) {
		p = player;
		if(aviable) {
			player.world.createExplosion(player, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), (float) (1), true);
			aviable = false;
			xp += level * 2.75;
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
