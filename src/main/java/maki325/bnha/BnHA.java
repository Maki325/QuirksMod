package maki325.bnha;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import maki325.bnha.api.capabilities.quirk.FactoryQuirk;
import maki325.bnha.api.capabilities.quirk.IQuirk;
import maki325.bnha.api.capabilities.quirk.QuirkProvider;
import maki325.bnha.api.capabilities.quirk.QuirkStorage;
import maki325.bnha.api.functional.Function;
import maki325.bnha.api.quirk.Quirk;
import maki325.bnha.gui.skilltree.GuiHandlerST;
import maki325.bnha.handlers.CapabilityHandler;
import maki325.bnha.net.messages.MessageFactory;
import maki325.bnha.proxy.CommonProxy;
import maki325.bnha.quirks.TestQuirk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

//Version is from mcmod.info
@Mod(modid=Reference.MOD_ID, name=Reference.MOD_NAME, dependencies=Reference.MOD_DEPENDENCIES, acceptedMinecraftVersions=Reference.MC_VERSION, useMetadata=true)
public class BnHA {
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
	public static CommonProxy proxy;
	
	@Instance
	public static BnHA instance;
	
	public static Logger logger;
	
	@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		logger.log(Level.INFO, "Pre Initialization Event Starting");
		long time = System.nanoTime();
		proxy.preInit();
		KeyBindings.init();
		CapabilityManager.INSTANCE.register(IQuirk.class, new QuirkStorage(), new FactoryQuirk());
		
		MinecraftForge.EVENT_BUS.register(BnHA.class);
		
		logger.log(Level.INFO, "Pre Initialization Event Finished In: " + (System.nanoTime() - time) + " nanoseconds.");
	}
	
	@Mod.EventHandler
    public void init(FMLInitializationEvent event) {
		logger.log(Level.INFO, "Initialization Event Starting");
		long time = System.nanoTime();
		proxy.init();
		
		logger.log(Level.INFO, "Initialization Event Finished In: " + (System.nanoTime() - time) + " nanoseconds.");
	}

	@Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
		logger.log(Level.INFO, "Post Initialization Event Starting");
		long time = System.nanoTime();
		
		logger.log(Level.INFO, "Post Initialization Finished In: " + (System.nanoTime() - time) + " nanoseconds.");
	}

	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent event) {}
	
	@SubscribeEvent
	public static void onKeyInput(ClientTickEvent event) {
		if(KeyBindings.activate.isPressed()){
			NBTTagCompound prepareData = new NBTTagCompound();
			prepareData.setString("message", "COOL");
			Function processData = (data, world, pos, player)  -> {
				System.out.println("Printing on Server");
				System.out.println("Message is: " + data.getString("message"));

				IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
	    		Quirk q = iquirk.getQuirks().get(0);
	    		if(q == null) {
		    		return;
	    		}
	    		q.onPlayerUse((EntityPlayerMP) player, (WorldServer) world);
			};
			NBTTagCompound prepareDataR = new NBTTagCompound();
			prepareDataR.setString("message", "RCOOL");
			prepareDataR.setIntArray("intArr", new int[] {7, 5, 3});
			Function processDataR = (data, world, pos, player)  -> {
				System.out.println("Printing on Client");
				System.out.println("Message is: " + data.getString("message"));
				System.out.println("Array is: " + data.getIntArray("intArr")[0]);
			};
			
			MessageFactory.sendDataToServerWithResponse("lol", BlockPos.ORIGIN, prepareData, processData, prepareDataR, processDataR);
		}

		if(KeyBindings.test.isPressed()){
	    	EntityPlayer p = Minecraft.getMinecraft().player;
	    	p.openGui(BnHA.instance, GuiHandlerST.getGuiID(), Minecraft.getMinecraft().world, p.getPosition().getX(), p.getPosition().getY(), p.getPosition().getZ());
		}
	}
	
	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event) {
		if(!event.isWasDeath()) return;
		EntityPlayer player = event.getEntityPlayer();
		IQuirk qurik = player.getCapability(QuirkProvider.QUIRK_CAP, null); 
		IQuirk oldQuirk = event.getOriginal().getCapability(QuirkProvider.QUIRK_CAP, null);
		if(!oldQuirk.getQuirks().isEmpty())
			MinecraftForge.EVENT_BUS.unregister(oldQuirk.getQuirks().get(0));
	
		oldQuirk.getQuirks().forEach(q -> {
			qurik.addQuirks(q);
		});
		
		if(!qurik.getQuirks().isEmpty())
			MinecraftForge.EVENT_BUS.register(qurik.getQuirks().get(0));
	}
	
	@SubscribeEvent
	public static void onPlayerJoin(PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;
		IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
		if(iquirk.getQuirks() == null) return;
		if(iquirk.getQuirks().isEmpty() || iquirk.getQuirks().get(0) == null) {
			iquirk.addQuirks(new TestQuirk());
			player.sendMessage(new TextComponentString("NEW"));
		}
		
		MinecraftForge.EVENT_BUS.register(iquirk.getQuirks().get(0));
		
		player.sendMessage(new TextComponentString("Your quirk is " + iquirk.getQuirks().get(0).getId()));
	}
	
	@SubscribeEvent
	public static void onPlayerLeave(PlayerLoggedOutEvent event) {
		EntityPlayer player = event.player;
		IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
		if(!iquirk.getQuirks().isEmpty())
			MinecraftForge.EVENT_BUS.unregister(iquirk.getQuirks().get(0));
	}
	
}

class KeyBindings {

	public static KeyBinding activate, test;
	
	public static void init() {
		activate = new KeyBinding("key.activate", Keyboard.KEY_Y, "key.categories." + Reference.MOD_ID);
		test = new KeyBinding("key.test", Keyboard.KEY_V, "key.categories." + Reference.MOD_ID);
		
		ClientRegistry.registerKeyBinding(activate);
		ClientRegistry.registerKeyBinding(test);
	}
	
}
