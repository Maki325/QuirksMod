package maki325.bnha.util.handlers;

import maki325.bnha.capability.providers.QuirkProvider;
import maki325.bnha.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler {

	public static final ResourceLocation QUIRK_CAP = new ResourceLocation(Reference.MOD_ID, "quirk"); 

	@SubscribeEvent 
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event) { 
		if (!(event.getObject() instanceof EntityPlayer)) return; 
	
		event.addCapability(QUIRK_CAP, new QuirkProvider()); 
	} 
	
}