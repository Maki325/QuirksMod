package maki325.bnha.gui.skilltree;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;

public class GuiST extends GuiScreen {

	public final static int xSize = 224;//224
	public final static int ySize = 134;//134
	private int posX = 0, posY = 0;
	private int tickCount = 0;
	
	private EntityPlayer player;
	
	public GuiST(EntityPlayer player) {
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
		posX = (width - xSize) / 2;
		posY = (height - ySize) / 2;
		
		drawRect(posX, posY, posX+xSize, posY+ySize, Color.ORANGE.getRGB());
		
		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();

		GlStateManager.color(0, 0, 1);
		drawLine(posX, posY, posX+xSize, posY+ySize);

		GlStateManager.popMatrix();
		GlStateManager.popAttrib();
		
	}
	
	private String firstLetterCapital(String text) {
		return text.substring(0, 1).toUpperCase() + text.substring(1);
	}

	private boolean isInside(int mouseX, int mouseY, int startX, int startY, int endX, int endY) {
		if(Mouse.isButtonDown(0) && mouseX > startX && mouseX < endX) {
			if(mouseY > startY && mouseY < endY)
				return true;
		}
		return false;
	}
	
	private void drawLine(int x1, int y1, int x2, int y2) {
	    int color = Color.RED.getRGB();
	    float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double)x1, (double)y1, 0.0D).endVertex();
        bufferbuilder.pos((double)x2, (double)y2, 0.0D).endVertex();
        tessellator.draw();
        
        bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double)x1, (double)y1+1, 0.0D).endVertex();
        bufferbuilder.pos((double)x2-1, (double)y2, 0.0D).endVertex();
        tessellator.draw();
        
        bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double)x1+1, (double)y1, 0.0D).endVertex();
        bufferbuilder.pos((double)x2, (double)y2-1, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
	}
	
	public void println(String text) {
		System.out.println(text);
	}
	
}
