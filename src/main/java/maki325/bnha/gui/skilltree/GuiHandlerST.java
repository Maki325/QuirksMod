package maki325.bnha.gui.skilltree;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandlerST implements IGuiHandler {

	private static final int GUI_ID_ST = 45;
	public static int getGuiID() {return GUI_ID_ST;}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		if(ID == getGuiID()) {
			
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		if(ID == getGuiID()) {
			return new GuiST(player);
		}
		
		return null;
	}

}
