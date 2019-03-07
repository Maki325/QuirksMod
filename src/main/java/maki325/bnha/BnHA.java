package maki325.bnha;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import maki325.bnha.api.functional.Function;
import maki325.bnha.net.messages.MessageFactory;
import maki325.bnha.proxy.CommonProxy;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
		//MessageFactory.init();
		
		MinecraftForge.EVENT_BUS.register(BnHA.class);
		
		logger.log(Level.INFO, "Pre Initialization Event Finished In: " + (System.nanoTime() - time) + " nanoseconds.");
	}
	
	@Mod.EventHandler
    public void init(FMLInitializationEvent event) {
		logger.log(Level.INFO, "Initialization Event Starting");
		long time = System.nanoTime();
		
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
	}
	
}

class KeyBindings {

	public static KeyBinding activate;
	
	public static void init() {
		activate = new KeyBinding("key.activate", Keyboard.KEY_Y, "key.categories." + Reference.MOD_ID);
		
		ClientRegistry.registerKeyBinding(activate);
	}
	
}
