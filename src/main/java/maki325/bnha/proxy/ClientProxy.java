package maki325.bnha.proxy;

import maki325.bnha.net.messages.MessageFactory;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
		super.preInit();
		MessageFactory.initClient();
	}
	
	@Override
	public void init() {
		super.init();
	}
	
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
	
	@Override
	public ModelBase getModel(int id) {
		return null;
	}
	
	public static void registerModel() {
	}
	
	

}
