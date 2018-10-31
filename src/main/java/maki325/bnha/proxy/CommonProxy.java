package maki325.bnha.proxy;

import maki325.bnha.BnHA;
import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.factory.FactoryQuirk;
import maki325.bnha.capability.quirk.storage.QuirkStorage;
import maki325.bnha.gui.ofa.GuiHandlerOFA;
import maki325.bnha.gui.skilltree.GuiHandlerST;
import maki325.bnha.net.playerjoin.MessageHandlerAddQuirkServer;
import maki325.bnha.net.playerjoin.messages.MessageAddQuirk;
import maki325.bnha.net.points.MessageChangePoints;
import maki325.bnha.net.points.MessageHandlerChangePointsServer;
import maki325.bnha.net.quirk.MessageHandlerActivateClientServer;
import maki325.bnha.net.quirk.MessageHandlerActivateServer;
import maki325.bnha.net.quirk.MessageHandlerChangeQuirkServer;
import maki325.bnha.net.quirk.MessageHandlerRemoveQuirk;
import maki325.bnha.net.quirk.hud.MessageChangeHudSkill;
import maki325.bnha.net.quirk.hud.MessageHandlerChangeHudSkillServer;
import maki325.bnha.net.quirk.messages.MessageActivate;
import maki325.bnha.net.quirk.messages.MessageActivateClient;
import maki325.bnha.net.quirk.messages.MessageChangeQuirk;
import maki325.bnha.net.quirk.messages.MessageRemoveQuirk;
import maki325.bnha.net.tag.add.MessageAddTag;
import maki325.bnha.net.tag.add.MessageHandlerAddTagServer;
import maki325.bnha.net.tag.remove.MessageHandlerRemoveTagServer;
import maki325.bnha.net.tag.remove.MessageRemoveTag;
import maki325.bnha.util.GuiHandlerRegistry;
import maki325.bnha.util.Reference;
import maki325.bnha.util.handlers.EventHandlerServer;
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
	
	public static final byte ACTIVATE_CLIENT_MESSAGE_ID = 30;
	public static final byte ACTIVATE_SERVER_MESSAGE_ID = 39;
	public static final byte ACTIVATE_CLIENT_SERVER_MESSAGE_ID = 40;
	//REMOVE QUIRK
	public static final byte REMOVE_QUIRK_MESSAGE_ID = 42;
	//ADD QUIRK
	public static final byte ADD_QUIRK_MESSAGE_ID = 59;
	public static final byte ADD_QUIRK_SERVER_MESSAGE_ID = 60;
	//CHANGE QUIRK
	public static final byte CHANGE_QUIRK_CLIENT_MESSAGE_ID = 67;
	public static final byte CHANGE_QUIRK_SERVER_MESSAGE_ID = 68;
	
	public static final byte CHANGE_ADD_TAG_CLIENT_MESSAGE_ID = 5;
	public static final byte CHANGE_REMOVE_TAG_CLIENT_MESSAGE_ID = 6;
	
	public static final byte CHANGE_ADD_TAG_SERVER_MESSAGE_ID = 7;
	public static final byte CHANGE_REMOVE_TAG_SERVER_MESSAGE_ID = 8;

	//POINT SYSTEM
	public static final byte POINTS_SERVER_MESSAGE_ID = 17;
	public static final byte POINTS_CLIENT_MESSAGE_ID = 18;
	

	//HUD SKILL SYSTEM
	public static final byte HUD_SKILL_SERVER_MESSAGE_ID = 10;
	public static final byte HUD_SKILL_CLIENT_MESSAGE_ID = 11;

	public void registerItemRenderer(Item item, int meta, String id) {}
	
	public void preInit() {
		
		CapabilityManager.INSTANCE.register(IQuirk.class, new QuirkStorage(), new FactoryQuirk());
		
		simpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
		
		simpleNetworkWrapper.registerMessage(MessageHandlerActivateServer.class, MessageActivate.class, ACTIVATE_SERVER_MESSAGE_ID, Side.SERVER);
		
		simpleNetworkWrapper.registerMessage(MessageHandlerRemoveQuirk.class, MessageRemoveQuirk.class, REMOVE_QUIRK_MESSAGE_ID, Side.SERVER);
		
		simpleNetworkWrapper.registerMessage(MessageHandlerAddQuirkServer.class, MessageAddQuirk.class, ADD_QUIRK_SERVER_MESSAGE_ID, Side.SERVER);
		
		simpleNetworkWrapper.registerMessage(MessageHandlerActivateClientServer.class, MessageActivateClient.class, ACTIVATE_CLIENT_SERVER_MESSAGE_ID, Side.SERVER);
		
		simpleNetworkWrapper.registerMessage(MessageHandlerChangeQuirkServer.class, MessageChangeQuirk.class, CHANGE_QUIRK_SERVER_MESSAGE_ID, Side.SERVER);
		
		//TAG
		simpleNetworkWrapper.registerMessage(MessageHandlerAddTagServer.class, MessageAddTag.class, CHANGE_ADD_TAG_SERVER_MESSAGE_ID, Side.SERVER);
		simpleNetworkWrapper.registerMessage(MessageHandlerRemoveTagServer.class, MessageRemoveTag.class, CHANGE_REMOVE_TAG_SERVER_MESSAGE_ID, Side.SERVER);
		
		//POINT SYSTEM
		simpleNetworkWrapper.registerMessage(MessageHandlerChangePointsServer.class, MessageChangePoints.class, POINTS_SERVER_MESSAGE_ID, Side.SERVER);
		
		//HUD SKILL SYSTEM
		simpleNetworkWrapper.registerMessage(MessageHandlerChangeHudSkillServer.class, MessageChangeHudSkill.class, HUD_SKILL_SERVER_MESSAGE_ID, Side.SERVER);
		
		
		MinecraftForge.EVENT_BUS.register(EventHandlerServer.class);

		//Registering Gui Handler Registry(GHR)
		NetworkRegistry.INSTANCE.registerGuiHandler(BnHA.instance, GuiHandlerRegistry.getInstance());
		
		//Adding Gui Handlers to GHR
		GuiHandlerRegistry.getInstance().registerGuiHandler(new GuiHandlerOFA(), GuiHandlerOFA.getGuiID());
		GuiHandlerRegistry.getInstance().registerGuiHandler(new GuiHandlerST(), GuiHandlerST.getGuiID());
		
		
	}

	public void init() {}
	
	public EntityPlayer getPlayer(MessageContext ctx) {
		return ctx.getServerHandler().player;
	}

	public void postInit() {}
	
}
