package maki325.bnha.gui.ofa;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandlerOFA implements IGuiHandler {

	private static final int GUI_ID_OFA = 30;
	public static int getGuiID() {return GUI_ID_OFA;}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		if(ID == getGuiID()) {
			
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		if(ID == getGuiID()) {
			return new GuiOFA(player);
		}
		
		return null;
	}

}
