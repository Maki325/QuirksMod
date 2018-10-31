package maki325.bnha.quirks;

import maki325.bnha.api.LevelUp;
import maki325.bnha.api.Quirk;
import maki325.bnha.util.Reference;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class QuirkHellFlame extends Quirk {
	
	public QuirkHellFlame() {
		super("hellflame", Reference.MOD_ID);
		
		setMaxCooldown(2000);
		setLevelMinimum(100);
		setLevelFactor(5);
		setMaxActivatedTime(1);
		setLevelUp(new LevelUp(0, 0.99));
		setXpPerTick(1/20);
		
		init();
	}

	@Override
	public void onPlayerUse(EntityPlayerMP player) {
		p = player;
		if(aviable) {
			player.world.newExplosion(player, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), (float) (2), true, false);

			aviable = false;
			xp += level * 2.75;
		}
	}

	@SubscribeEvent
	public void tick(ServerTickEvent event) {
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
	

	@Override
	public NBTTagCompound save() { return new NBTTagCompound(); }

	@Override
	public void load(NBTTagCompound tag) {}
	
}
