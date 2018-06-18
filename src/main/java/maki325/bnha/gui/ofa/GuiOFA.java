package maki325.bnha.gui.ofa;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import org.lwjgl.input.Keyboard;

import maki325.bnha.util.Reference;
import maki325.bnha.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class GuiOFA extends GuiScreen {
	
	public final static int xSize = 224;//224
	public final static int ySize = 134;//134
	
	private EntityPlayer player;
	
	private List<EntityPlayer> players;
	private int page = 1;
	
	private static final ResourceLocation background = new ResourceLocation(Reference.MOD_ID, "textures/gui/background.png");
	private static final ResourceLocation tab = new ResourceLocation(Reference.MOD_ID, "textures/gui/tab.png");
	
	public GuiOFA(EntityPlayer player) {
		players = player.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(new BlockPos(player.posX-5, player.posY-5, player.posZ-5), new BlockPos(player.posX+5, player.posY+5, player.posZ+5)));
		this.player = player;
	}
	
	@Override
	public void initGui() {
		 Keyboard.enableRepeatEvents(true);
	}
	
	@Override
	public void onResize(Minecraft mcIn, int w, int h) {
		super.onResize(mcIn, w, h);
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}
		
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
		int posX = (width - xSize) / 2;
		int posY = (height - ySize) / 2;
		
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);

		String text = "One For All - Transfer";
		FontRenderer f = mc.fontRenderer;
		f.drawString(text, posX + xSize/2 - f.getStringWidth(text)/2, posY+5, Color.white.getRGB(), true);
		
		//System.out.println(players.get(0).getName());
		
		for(int i = 1;i <= 4;i++) {
			if(i*page > players.size()) { break; }
			
			drawPlayerTab(posX+15, posY+15 + 25*(i-1), players.get(i*page-1), partialTicks);
		}
		 
		/*drawPlayerTab(posX+15, posY+15, player, partialTicks);
		 
		drawPlayerTab(posX+15, posY+15+20 + 5, player, partialTicks);
		 
		drawPlayerTab(posX+15, posY+15+50, player, partialTicks);
		 
		drawPlayerTab(posX+15, posY+15+75, player, partialTicks);*/
		

		mc.getTextureManager().bindTexture(tab);
		
		drawTexturedModalRect(posX + xSize/2+16, posY + 115, 225, 0, 16, 16);
		drawTexturedModalRect(posX + xSize/2-32, posY + 115, 225, 16, 16, 16);
		 
	}
	
	public void drawPlayerTab(int x, int y, EntityPlayer p, float partialTicks) {
		mc.getTextureManager().bindTexture(tab);
		drawTexturedModalRect(x, y, 0, 0, 194, 20);
		 
		drawTexturedModalRect(x + 173, y+2, 200, 0, 16, 16);

		drawString(Minecraft.getMinecraft().fontRenderer, (p.getName().length() > 20) ? p.getName().substring(0, 28) + "..." : p.getName(), x+5, y+5, Color.white.getRGB());
		 
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		int posX = (width - xSize) / 2;
		int posY = (height - ySize) / 2;
		
		if(mouseButton == 0) {
			if(Utils.isMouseInside(mouseX, mouseY, posX+15+173, posY+15+2, posX+15+173+16, posY+15+2+16)) {
				System.out.println("PLAYER AT: " + (1*page-1));
			} else if(Utils.isMouseInside(mouseX, mouseY, posX+15+173, posY+15+25+2, posX+15+173+16, posY+15+25+2+16)) {
				System.out.println("PLAYER AT: " + (1*page-1 + 1));
			} else if(Utils.isMouseInside(mouseX, mouseY, posX+15+173, posY+15+50+2, posX+15+173+16, posY+15+50+2+16)) {
				System.out.println("PLAYER AT: " + (1*page-1 + 2));
			} else if(Utils.isMouseInside(mouseX, mouseY, posX+15+173, posY+15+75+2, posX+15+173+16, posY+15+75+2+16)) {
				System.out.println("PLAYER AT: " + (1*page-1 + 3));
			} else if(Utils.isMouseInside(mouseX, mouseY, posX + xSize/2+16, posY + 115, posX + xSize/2+16 + 16, posY + 115 + 16)) {
				if(4*page < players.size()) {
					page++;
				}
			} else if(Utils.isMouseInside(mouseX, mouseY, posX + xSize/2-32, posY + 115, posX + xSize/2-32 + 16, posY + 115 + 16)) {
				if(page != 1) {
					page--;
				}
			}
		}
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
 	
}
