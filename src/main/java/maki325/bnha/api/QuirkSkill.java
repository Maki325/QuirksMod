package maki325.bnha.api;

import java.util.HashMap;

import maki325.bnha.BnHA;
import maki325.bnha.api.skilltree.Skill;
import maki325.bnha.api.skilltree.Skilltree;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import maki325.bnha.net.points.MessageChangePoints;
import maki325.bnha.net.points.MessageChangePoints.Change;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public abstract class QuirkSkill extends Quirk {

	public HashMap<Skill, Integer> cooldowns = new HashMap<Skill, Integer>();
	public HashMap<Skill, Boolean> aviables = new HashMap<Skill, Boolean>();
	
	protected Skilltree skillTree;
	
	protected boolean isSuccessful;
	
	public QuirkSkill(String name, String modId, Skilltree tree) {
		super(name, modId);
		this.skillTree = tree;
	}
	
	@Override
	public void init() {
		super.init();
		skillTree.init();
		for(int i = 0;i < skillTree.getHudSkills().length;i++) {
			Skill s = skillTree.getHudSkills()[i];
			if(s == null) continue;
			if(!aviables.containsKey(s)) {
				aviables.put(s, true);
			}
			if(!cooldowns.containsKey(s)) {
				cooldowns.put(s, 0);
			}
		}
	}

	@Override
	public void onPlayerUse(EntityPlayerMP player) {
		isSuccessful = false;
		if(skillTree.getHudSkills() != null && skillTree.getHudSkills()[0] != null && aviables.get(skillTree.getHudSkills()[0])) {
			Skill s = skillTree.getHudSkills()[0];
			s.onActivate(player);
			aviables.replace(s, false);
			
			//Points
			player.getCapability(QuirkProvider.QUIRK_CAP, null).addPoints(s.getMoneyMake());
			BnHA.proxy.simpleNetworkWrapper.sendTo(new MessageChangePoints(s.getMoneyMake(), Change.ADD, player.getName()), player);
			isSuccessful = true;
		}
	}
	
	@SubscribeEvent
	public void tick(ServerTickEvent event) {
		for(int i = 0;i < skillTree.getHudSkills().length;i++) {
			Skill s = skillTree.getHudSkills()[i];
			if(s == null) continue;
			if(aviables.get(s) == null) {
				aviables.put(s, true);
				cooldowns.put(s, 0);
				continue;
			}
			if(!aviables.get(s)) {
				cooldowns.replace(s, cooldowns.get(s)+1);
				if(cooldowns.get(s) >= s.getMaxCooldown()) {
					aviables.replace(s, true);
					cooldowns.replace(s, 0);
				}
			}
		}
	}
	
	public NBTTagCompound save() {
		NBTTagCompound tag = new NBTTagCompound();
		
		if(skillTree == null) return tag;

		tag.setTag("skilltree_info", skillTree.save());
		
		for(int i = 0;i < skillTree.getHudSkills().length && i < aviables.size();i++) {
			tag.setBoolean("aviable_" + i, (boolean) aviables.values().toArray()[i]);
			tag.setInteger("cooldown_" + i, (int) cooldowns.values().toArray()[i]);
		}
		
		return tag;
	}
	
	public void load(NBTTagCompound tag) {
		if(skillTree != null) {
			skillTree.load(tag.getCompoundTag("skilltree_info"));
			
			for(int i = 0;i < skillTree.getHudSkills().length && i < aviables.size();i++) {
				aviables.replace((Skill) skillTree.getHudSkills()[i], tag.getBoolean("aviable_" + i));
				cooldowns.replace((Skill) skillTree.getHudSkills()[i], tag.getInteger("cooldown_" + i));
			}
		}
	}
	
	/**
	 * 
	 * @return the skill tree of the quirk or null if the quirk doesn't have one
	 */
	public Skilltree getSkilltree() {
		return skillTree;
	}
	
	protected void setSkilltree(Skilltree tree) {
		this.skillTree = tree;
	}

}
