package maki325.bnha.proxy;

import maki325.bnha.BnHA;
import maki325.bnha.capability.IQuirk;
import maki325.bnha.capability.factory.FactoryQuirk;
import maki325.bnha.capability.storage.QuirkStorage;
import maki325.bnha.gui.ofa.GuiHandlerOFA;
import maki325.bnha.net.MessageHandlerActivateServer;
import maki325.bnha.net.MessageHandlerGui;
import maki325.bnha.net.MessageHandlerRemoveQuirk;
import maki325.bnha.net.messages.MessageActivate;
import maki325.bnha.net.messages.MessageGui;
import maki325.bnha.net.messages.MessageRemoveQuirk;
import maki325.bnha.util.Reference;
import maki325.bnha.util.handlers.KeyInputHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {

	public static SimpleNetworkWrapper simpleNetworkWrapper;
	
	public static final byte ACTIVATE_CLIENT_MESSAGE_ID = 38;
	public static final byte ACTIVATE_SERVER_MESSAGE_ID = 39;
	public static final byte REMOVE_QUIRK_MESSAGE_ID = 40;

	public static final byte GUI_MESSAGE_ID = 41;
	
	public void registerItemRenderer(Item item, int meta, String id) {}
	
	public void preInit() {
		
		CapabilityManager.INSTANCE.register(IQuirk.class, new QuirkStorage(), new FactoryQuirk());
		
		simpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
		
		simpleNetworkWrapper.registerMessage(MessageHandlerActivateServer.class, MessageActivate.class, ACTIVATE_SERVER_MESSAGE_ID, Side.SERVER);
		
		simpleNetworkWrapper.registerMessage(MessageHandlerRemoveQuirk.class, MessageRemoveQuirk.class, REMOVE_QUIRK_MESSAGE_ID, Side.SERVER);
		
		simpleNetworkWrapper.registerMessage(MessageHandlerGui.class, MessageGui.class, GUI_MESSAGE_ID, Side.SERVER);
		
		NetworkRegistry.INSTANCE.registerGuiHandler(BnHA.instance, new GuiHandlerOFA());
		
	}

	public void init() {
		
		MinecraftForge.EVENT_BUS.register(KeyInputHandler.class);
	}
	
	public EntityPlayer getPlayer(MessageContext ctx) {
		return ctx.getServerHandler().player;
	}
	
}
