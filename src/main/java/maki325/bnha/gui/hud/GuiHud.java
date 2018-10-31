package maki325.bnha.gui.hud;

import java.awt.Color;
import java.util.List;

import maki325.bnha.api.Pair;
import maki325.bnha.api.Quirk;
import maki325.bnha.api.QuirkRegistry;
import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import maki325.bnha.quirks.QuirkNone;
import maki325.bnha.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiHud extends Gui {
	
	public final static int xSize = 166;//224
	public final static int ySize = 34;//134
	
	public static final ResourceLocation QUIRK_TEXTURES = new ResourceLocation(Reference.MOD_ID, "textures/atlas/quirks.png");
	//= new ResourceLocation(Reference.MOD_ID, "textures/quirks/quirks.png");
	
	Minecraft mc;
	
	public GuiHud(Minecraft mc) {
		this.mc = mc;
	}
	
	public void render(int screenWidth, int screenHeight) {
		ResourceLocation hud = new ResourceLocation(Reference.MOD_ID, "textures/gui/hud/hud.png");
		
		int x = 10 * screenWidth / 1336, y = 10 * screenHeight / 1336;
		
		mc.getTextureManager().bindTexture(hud);
		/*GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();
		GlStateManager.scale(1.1f, 1.1f, 1.1f);*/
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		/*GlStateManager.popMatrix();
		GlStateManager.popAttrib();*/
		
		IQuirk iquirk = Minecraft.getMinecraft().player.getCapability(QuirkProvider.QUIRK_CAP, null);
		if(iquirk == null || iquirk.getQuirks() == null || iquirk.getQuirks().isEmpty()) {
			drawTexturedModalRect(x+1, y+1, 0, 0, 32, 32);
			return;
		}
		
		mc.getTextureManager().bindTexture(QUIRK_TEXTURES);
		
		//System.out.println("QUIRK IS: " + quirkName);
		
		// 3 * 20% of FULL BLACK
		
		Quirk q = iquirk.getQuirks().get(0);
		
		if(q != null && !q.isSimilar(new QuirkNone())) {
			Pair<Integer, Integer> uv = QuirkRegistry.getUV(q);
			// ‚GlStateManager.pushMatrix();
			//GlStateManager.scale(2, 2, 1);
			//int sizeofthig = 34;
			//drawRect(x+2, y+2, x+2+sizeofthig, y+2+sizeofthig, Color.BLACK.getRGB());
			drawTexturedModalRect(x+1, y+1, uv.getFirst(), uv.getSecond(), 32, 32);
			//GlStateManager.popMatrix();
			//drawTexturedModalRect(x+1, y+1, 0, 0, 128, 128);
		} else {
			drawTexturedModalRect(x+1, y+1, 0, 0, 32, 32);
		}
		
	}

}
