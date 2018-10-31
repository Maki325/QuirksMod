package maki325.bnha.gui.skilltree;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import maki325.bnha.BnHA;
import maki325.bnha.api.Pair;
import maki325.bnha.api.Quirk;
import maki325.bnha.api.QuirkSkill;
import maki325.bnha.api.skilltree.Skill;
import maki325.bnha.api.skilltree.SkillRegistry;
import maki325.bnha.api.skilltree.Skilltree;
import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import maki325.bnha.gui.hud.GuiHud;
import maki325.bnha.net.points.MessageChangePoints;
import maki325.bnha.net.quirk.hud.MessageChangeHudSkill;
import maki325.bnha.net.quirk.hud.MessageChangeHudSkill.Change;
import maki325.bnha.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiST extends GuiScreen {

	public final static int xSize = 224;//224
	public final static int ySize = 134;//134
	private int posX = 0, posY = 0;
	private int tickCount = 0;
	private boolean blink = false;
	private HashMap<Skill, Boolean> activePoups;
	private boolean buttonsPressed[];
	
	private EntityPlayer player;
	
	private static final ResourceLocation background = new ResourceLocation(Reference.MOD_ID, "textures/gui/skilltree/skilltree.png");
	private static final ResourceLocation icons = new ResourceLocation(Reference.MOD_ID, "textures/gui/skilltree/icons.png");
	private static final ResourceLocation skill_background = new ResourceLocation(Reference.MOD_ID, "textures/gui/skilltree/background.png");
	public static final ResourceLocation SKILL_TEXTURES = new ResourceLocation(Reference.MOD_ID, "textures/atlas/skills.png");
	
	public GuiST(EntityPlayer player) {
		this.player = player;
		activePoups = new HashMap<Skill, Boolean>();
		buttonsPressed = new boolean[Mouse.getButtonCount()];
		for(int i = 0;i < Mouse.getButtonCount();i++)
			buttonsPressed[i] = false;
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
		
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);
		
		tickCount++;
		if(tickCount % 7 == 0) {
			blink = false;
		}
		if(tickCount % 30 == 0) {
			tickCount = 0;
			blink = true;
		}
		
		IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
		Quirk q = iquirk.getQuirks().get(0);
		
		if(!(q instanceof QuirkSkill)) {
			String eror = firstLetterCapital("This quirk doesn't have a skilltree");
			fontRenderer.drawString(eror, posX + xSize/2 - fontRenderer.getStringWidth(eror) / 2, posY+7, Color.RED.getRGB());
			return;
		}
		
		QuirkSkill quirkS = (QuirkSkill) q;
		
		Skilltree tree = quirkS.getSkilltree();
		if(tree == null) {
			player.sendMessage(new TextComponentString("This quirk doesn't have a skill tree."));
			return;
		}
		
		String name = firstLetterCapital(tree.getName());
		fontRenderer.drawString(name, posX + xSize/2 - fontRenderer.getStringWidth(name) / 2, posY+7, Color.RED.getRGB());
		
		String ps = iquirk.getPoints() + " points";
		fontRenderer.drawString(ps, posX + xSize - 50 - fontRenderer.getStringWidth(ps) / 2, posY+7, Color.RED.getRGB());
		
		mc.getTextureManager().bindTexture(GuiHud.QUIRK_TEXTURES);
		
		Skill root = tree.getRoot();
		drawSkill(root, posY + 20, posX + xSize/2 - 8, mouseX, mouseY);
		
		if(!Mouse.isButtonDown(0)) {
			buttonsPressed[0] = false;
		}
		clearPopups(0);
		resetClick(0);
		
	}
	
	private String firstLetterCapital(String text) {
		return text.substring(0, 1).toUpperCase() + text.substring(1);
	}
	
	private void resetClick(int button) {
		button = Math.min(button, buttonsPressed.length);
		if(!Mouse.isButtonDown(button)) {
			buttonsPressed[button] = false;
		}
	}
	
	private void clearPopups(int button) {
		button = Math.min(button, buttonsPressed.length);
		if(Mouse.isButtonDown(button) && buttonsPressed[button] == false) {
			activePoups.clear();
			buttonsPressed[button] = true;
		}
	}
	
	private void drawSkill(Skill skill, int yPos, int xPos, int mouseX, int mouseY) {
		if(xPos < posX || xPos > posX + xSize || yPos < posY || yPos > posY + ySize) return;

		GL11.glColor4f(1f, 1f, 1f, 1f);
		
		/*
		   Mouse:
			0 - left
			1 - right
		 */
		
		Pair<Integer, Integer> uv = SkillRegistry.getUV(skill);
		GlStateManager.pushMatrix();
		{
			GlStateManager.translate(0, 0, 5);
			GL11.glColor4f(0f, 0f, 0f, 1f);
			drawRect(xPos - 1, yPos - 1, xPos + 17, yPos, Color.BLACK.getRGB()); 	// TOP
			drawRect(xPos - 1, yPos - 1, xPos, yPos + 17, Color.BLACK.getRGB()); 	// LEFT
			drawRect(xPos + 16, yPos - 1, xPos + 17, yPos + 17, Color.BLACK.getRGB()); 	// RIGHT
			drawRect(xPos - 1, yPos + 16, xPos + 17, yPos + 17, Color.BLACK.getRGB()); 	// BOTTOM
			
			GL11.glColor4f(1f, 1f, 1f, 1f);
			mc.getTextureManager().bindTexture(skill_background);
			drawTexturedModalRect(xPos, yPos, 0, 0, 16, 16);
			GL11.glColor4f(1f, 1f, 1f, 1f);
			
			//SKILL DRAWING
			mc.getTextureManager().bindTexture(SKILL_TEXTURES);
			if(!skill.isActive()) {
				GL11.glColor4f(.6f, .6f, .6f, 1f);
				
				if(skill.getParent() != null && blink && skill.getParent().isActive())
					GL11.glColor4f(.8f, .8f, .8f, 1f);
			}
			drawTexturedModalRect(xPos, yPos, uv.getFirst(), uv.getSecond(), 16, 16);
		}
		GlStateManager.popMatrix();
		
		{
			int px = xPos + 16, yp = yPos - 12;
			int width = fontRenderer.getStringWidth(skill.getName()) + 10;
			if(Mouse.isButtonDown(0) && isInside(mouseX, mouseY, xPos, yPos, xPos + 12, yPos + 12)) {
				drawPopup(px, yp, yPos, width, mouseX, mouseY, skill);
				buttonsPressed[0] = true;
			}
			if(activePoups.containsKey(skill)) {
				drawPopup(px, yp, yPos, width, mouseX, mouseY, skill);
			}
			if(!Mouse.isButtonDown(0)) {
				buttonsPressed[0] = false;
			}
			GL11.glColor4f(1f, 1f, 1f, 1f);
		}
		
		List<Skill> children = skill.getChildren();
		
		if(children.size() == 0) return;
		
		GL11.glColor4f(0f, 0f, 1f, 1f);
		int middle = 0;
		GlStateManager.pushMatrix();
		{
			GlStateManager.translate(0, 0, 0);
			if(children.size() == 1)
				verticalLine(xPos + 7, yPos + 8, yPos + 8 + 32, 1, Color.BLUE.getRGB());
			else
				verticalLine(xPos + 7, yPos + 8, yPos + 8 + 25, 1, Color.BLUE.getRGB());
			
			middle = children.size()/2 * 32;
			horizontalLine(Math.max(xPos - middle + 7, posX), Math.min(posX+xSize, xPos + middle + 7), yPos + 8 + 25, 1, Color.BLUE.getRGB());

		}
		GlStateManager.popMatrix();
		
		
		if(children.size() == 1) {
			Skill child = children.get(0);
			drawSkill(child, yPos + 32, xPos, mouseX, mouseY);
			return;
		}
		for(int i = 0;i < children.size();i++) {
			if(xPos + i*32 < posX || xPos + i*32 > posX + xSize || yPos + 24 < posY || yPos + 24 > posY + ySize) continue;
			Skill child = children.get(i);
			
			if(i < children.size()-1)
				drawSkill(child, yPos + 26, xPos - middle + i*32, mouseX, mouseY);
			else 
				drawSkill(child, yPos + 26, xPos + i*32, mouseX, mouseY);
		}

	}
	
	@SideOnly(Side.CLIENT)
	private void drawPopup(int px, int yp, int yPos, int width, int mouseX, int mouseY, Skill skill) {
		activePoups.clear();
		activePoups.put(skill, true);
		GlStateManager.pushMatrix();
		{
			GlStateManager.translate(0, 0, 100);
			
			GL11.glColor4f(1f, 1f, 1f, 1f);
			mc.getTextureManager().bindTexture(icons);
			if(skill.isActive()) 
				drawTexturedModalRect(px - 6, yp + 2, 48, 0, 16, 16);	//ATTACH BUTTON
			else
				drawTexturedModalRect(px - 6, yp + 2, 32, 0, 16, 16);	//BUY BUTTON
			
			if(Mouse.isButtonDown(0) && isInside(mouseX, mouseY, px -6, yp + 2, px - 6 + 16, yp + 2 + 16)) {
				IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
				if(skill.isActive()) {
					//((QuirkSkill) iquirk.getQuirks().get(0)).getSkilltree().setHudSkill(skill, 0);
					BnHA.proxy.simpleNetworkWrapper.sendToServer(new MessageChangeHudSkill(skill.getName(), 0, MessageChangeHudSkill.Change.SET, player.getName()));
				} else {
					if(iquirk.getPoints() >= skill.getPrice()) {
						skill.setActive(true);
						//iquirk.removePoints(skill.getPrice());
						BnHA.proxy.simpleNetworkWrapper.sendToServer(new MessageChangePoints(skill.getPrice(), MessageChangePoints.Change.REMOVE, player.getName()));
						player.sendMessage(new TextComponentString("You bought skill " + skill.getName() + " for " + skill.getPrice() + " points. You now have " + (iquirk.getPoints()-skill.getPrice()) + " points."));
					} else {
						player.sendMessage(new TextComponentString("You don't have enough points to buy the skill.You have " + iquirk.getPoints() + " points and the skill costs " + skill.getPrice() + " points."));
					}
				}
				buttonsPressed[0] = true;
			}
		}
		GlStateManager.popMatrix();
		
		if(player.getTags().contains(Reference.MOD_ID + ":skill_op") && skill.getParent() != null&& skill.getParent().isActive()) {
			GlStateManager.pushMatrix();
			{
				GlStateManager.translate(0, 0, 100);
	
				//ACTIVATE BUTTON
				mc.getTextureManager().bindTexture(icons);
				drawTexturedModalRect(px - 6, yp + 2 + 20, 0, 0, 16, 16);
				
				if(Mouse.isButtonDown(0) && isInside(mouseX, mouseY, px - 6, yp + 22, px - 6 + 16, yp + 22 + 16)) {
					println("ACTIVATE");
					skill.setActive(true);
					buttonsPressed[0] = true;
				}
	
				//DEACTIVATE BUTTON
				mc.getTextureManager().bindTexture(icons);
				drawTexturedModalRect(px - 26, yp + 2 + 20, 16, 0, 16, 16);
				
				if(Mouse.isButtonDown(0) && isInside(mouseX, mouseY, px - 26, yp + 22, px - 26 + 16, yp + 22 + 16)) {
					println("DEACTIVE");
					skill.setActive(false);
					buttonsPressed[0] = true;
				}
			}
			GlStateManager.popMatrix();
		}
	}
	
	private boolean isInside(int mouseX, int mouseY, int startX, int startY, int endX, int endY) {
		if(Mouse.isButtonDown(0) && mouseX > startX && mouseX < endX) {
			if(mouseY > startY && mouseY < endY)
				return true;
		}
		return false;
	}
	
	private void verticalLine(int x, int startY, int endY, int width, int color) {
		if (endY < startY)
        {
            int i = startY;
            startY = endY;
            endY = i;
        }
		if(width % 2 == 1) {
	        drawRect(x - width/2, startY + 1, x + width/2 + 1, endY, color);
			return;
		}

        drawRect(x - width/2, startY + 1, x + width/2, endY, color);
	}
	
	private void horizontalLine(int startX, int endX, int y, int height, int color) {
		if (endX < startX)
        {
            int i = startX;
            startX = endX;
            endX = i;
        }
		if(height % 2 == 1) {
	        drawRect(startX, y - height/2, endX + 1, y + 1 + height/2, color);
	        return;
		}

        drawRect(startX, y - height/2, endX + 1, y + height/2, color);
	}
	
	public void println(String text) {
		System.out.println(text);
	}
	
}
