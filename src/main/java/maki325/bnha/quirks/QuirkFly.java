package maki325.bnha.quirks;

import maki325.bnha.api.LevelUp;
import maki325.bnha.api.Quirk;
import maki325.bnha.util.Reference;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class QuirkFly extends Quirk {
	
	public QuirkFly() {
		super("fly", Reference.MOD_ID);

		setMaxCooldown(200);
		setMaxActivatedTime(200);
		setLevelFactor(1.5);
		setLevelMinimum(10);
		setLevelUp(new LevelUp(1.1d, 0.9d));
		setXpPerTick(1.5/20);
		
		init();
	}

	@Override
	public void onPlayerUse(EntityPlayerMP player) {
		p = player;
		if(!aviable) { return; }
		if(!activated) {
				player.capabilities.allowFlying = true;
				activated = true;
				
				player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Quirk Activated"));
				player.world.spawnParticle(EnumParticleTypes.DRAGON_BREATH, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 1, 1, 1, 0);
				
				player.sendPlayerAbilities();
		} else {
			if(!player.isCreative()) {
				player.capabilities.allowFlying = false;
				player.capabilities.isFlying = false;
			}
			
			activated = false;
			if(act >= maxAct) {
				aviable = false;
				player.fallDistance = 0;
				cooldown = maxCooldown * (maxAct/act);
				act = 0;
			}

			player.sendMessage(new TextComponentString(TextFormatting.RED + "Quirk Deactivated"));
			player.sendPlayerAbilities();
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
		if(activated) {
			act++;
			addXp(xpPerTick);
			if(xp >= nextXp) {
				level++;
				nextXp = xp * levelFactor;
				p.sendMessage(new TextComponentString(TextFormatting.AQUA + "Level Up"));
				p.sendMessage(new TextComponentString(TextFormatting.AQUA + "Your now level " + level));
				maxCooldown *= levelUp.getCooldownMultiplier();
				maxAct *= levelUp.getActivatedMultiplier();		
			}
			if(act >= maxAct) {
				activated = false;
				
				if(!p.isCreative()) {
					p.capabilities.allowFlying = false;
					p.capabilities.isFlying = false;
				}
				
				activated = false;
				aviable = false;
				p.fallDistance = 0;
				
				p.sendPlayerAbilities();
				
				act = 0;
				p.sendMessage(new TextComponentString(TextFormatting.RED + "Quirk Deactivated"));
			}
		}
	}

	@Override
	protected NBTTagCompound save() { return new NBTTagCompound(); }

	@Override
	protected void load(NBTTagCompound tag) {}
	
	
	
}
