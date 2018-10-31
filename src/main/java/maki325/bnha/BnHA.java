package maki325.bnha;

import java.lang.reflect.AnnotatedElement;

import org.apache.logging.log4j.Logger;

import maki325.bnha.api.QuirkRegistry;
import maki325.bnha.api.QuirkSkill;
import maki325.bnha.commands.CommandQuirk;
import maki325.bnha.proxy.CommonProxy;
import maki325.bnha.quirks.QuirkFly;
import maki325.bnha.quirks.QuirkHellFlame;
import maki325.bnha.quirks.explosion.QuirkExplosive;
import maki325.bnha.util.Reference;
import maki325.bnha.util.handlers.CapabilityHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "required-after:forge@[14.23.4.2705,)", acceptedMinecraftVersions = "[1.12,1.12.2]", useMetadata = true)
public class BnHA {

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static CommonProxy proxy;
	
	@Instance
	public static BnHA instance;
	
	public static Logger logger;
	
	@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
		
		logger = event.getModLog();
		
		QuirkRegistry.addQuirk(new QuirkExplosive());
		QuirkRegistry.addQuirk(new QuirkFly());
		QuirkRegistry.addQuirk(new QuirkHellFlame());
		
		QuirkRegistry.registerQuirkInstance(QuirkSkill.class);
		
		proxy.preInit();
	}
	
	@Mod.EventHandler
    public void init(FMLInitializationEvent event) {
		proxy.init();

		MinecraftForge.EVENT_BUS.register(CapabilityHandler.class);
	}

	@Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
	}

	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandQuirk());
	}
	
}
