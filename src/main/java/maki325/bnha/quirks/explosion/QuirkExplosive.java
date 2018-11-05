package maki325.bnha.quirks.explosion;

import maki325.bnha.api.LevelUp;
import maki325.bnha.api.QuirkSkill;
import maki325.bnha.quirks.explosion.skilltree.SkilltreeExplosive;
import maki325.bnha.util.Reference;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class QuirkExplosive extends QuirkSkill {
	
	public QuirkExplosive() {
		super("explosive", Reference.MOD_ID, new SkilltreeExplosive());
		
		setMaxCooldown(2000);
		setLevelMinimum(1000);
		setLevelFactor(5);
		setMaxActivatedTime(1);
		setLevelUp(new LevelUp(0, 0.99));
		setXpPerTick(1/20);
		
		init();
	}
	
	@SubscribeEvent @Override
	public void tick(ServerTickEvent event) {
		super.tick(event);
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
