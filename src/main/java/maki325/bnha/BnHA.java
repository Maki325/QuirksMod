package maki325.bnha;

import maki325.bnha.capability.IQuirk;
import maki325.bnha.capability.providers.QuirkProvider;
import maki325.bnha.init.KeyBindings;
import maki325.bnha.init.ModQuirks;
import maki325.bnha.proxy.CommonProxy;
import maki325.bnha.util.Reference;
import maki325.bnha.util.handlers.CapabilityHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "required-after:forge@[14.23.4.2705,)", acceptedMinecraftVersions = "[1.12,1.12.2]")
public class BnHA {

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static CommonProxy proxy;
	
	@Instance
	public static BnHA instance;
	
	@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
		
		KeyBindings.init();
		
		ModQuirks.init();
		
		proxy.preInit();
	}
	
	@Mod.EventHandler
    public void init(FMLInitializationEvent event) {
		
		proxy.init();
		
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(CapabilityHandler.class);
		ModQuirks.registerEvents(MinecraftForge.EVENT_BUS);
	}

	@Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

	}
	
	@SubscribeEvent 
	public void onPlayerClone(PlayerEvent.Clone event) { 
		EntityPlayer player = event.getEntityPlayer(); 
		IQuirk mana = player.getCapability(QuirkProvider.QUIRK_CAP, null); 
		IQuirk oldMana = event.getOriginal().getCapability(QuirkProvider.QUIRK_CAP, null); 
	
		oldMana.getQuirks().forEach(q -> mana.addQuirks(q));
	}
	
}
