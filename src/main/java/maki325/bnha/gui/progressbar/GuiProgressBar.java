package maki325.bnha.gui.progressbar;

import java.awt.Color;

import maki325.bnha.api.Quirk;
import maki325.bnha.api.QuirkSkill;
import maki325.bnha.capability.quirk.IQuirk;
import maki325.bnha.capability.quirk.providers.QuirkProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class GuiProgressBar extends Gui {

	private Minecraft mc;
	
	public GuiProgressBar(Minecraft mc) {
		this.mc = mc;
	}
	
	public void render(int screenWidth, int screenHeight) {
		
		int x = screenWidth - 150, y = screenHeight - 50;
		
		IQuirk iquirk = Minecraft.getMinecraft().player.getCapability(QuirkProvider.QUIRK_CAP, null);
		if(iquirk == null || iquirk.getQuirks() == null || iquirk.getQuirks().isEmpty()) {
			return;
		}
		
		Quirk q = iquirk.getQuirks().get(0);
		if(q == null || q instanceof QuirkSkill) return;
		
		int cooldownProgress = q.getCooldownProgress();
		int activeProgress = q.getActiveProgress();
		
		if(activeProgress != 100 && activeProgress != 0) {
			drawRect(x, y, x + 110, y + 24, Color.BLACK.getRGB());
			//q.getCooldown() == 0 
			drawRect(x + 5, y + 2, x + 105 - activeProgress, y + 2 + 20, Color.GREEN.getRGB());
		}
		
		//System.out.println(activeProgress);
		
		if(cooldownProgress != 100 && cooldownProgress != 0) {
			drawRect(x, y, x + 110, y + 24, Color.BLACK.getRGB());
			//q.getCooldown() == 0 
			drawRect(x + 5, y + 2, x + 5 + cooldownProgress, y + 2 + 20, Color.RED.getRGB());
		}
		
	}
	
}
