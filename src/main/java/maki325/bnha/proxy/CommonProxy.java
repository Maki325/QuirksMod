package maki325.bnha.proxy;

import maki325.bnha.BnHA;
import maki325.bnha.gui.skilltree.GuiHandlerST;
import maki325.bnha.handlers.CapabilityHandler;
import maki325.bnha.handlers.GuiHandler;
import maki325.bnha.net.messages.MessageFactory;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy {

	public void registerItemRenderer(Item item, int meta, String id) {}

	public void preInit() {
		MessageFactory.initCommon();
		
		//Registering Gui Handler Registry(GHR)
		NetworkRegistry.INSTANCE.registerGuiHandler(BnHA.instance, GuiHandler.getInstance());
		
		GuiHandler.getInstance().registerGuiHandler(new GuiHandlerST(), GuiHandlerST.getGuiID());
	}
	
	public void init() {
		MinecraftForge.EVENT_BUS.register(CapabilityHandler.class);
	}
	
	public ModelBase getModel(int id) {
		return null;
	}

	public EntityPlayer getPlayer(MessageContext ctx) {
		return ctx.getServerHandler().player;
	}
	
}
