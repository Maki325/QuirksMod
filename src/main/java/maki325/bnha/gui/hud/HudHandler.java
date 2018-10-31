package maki325.bnha.gui.hud;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HudHandler {

	private GuiHud hud;
	
	public HudHandler(GuiHud hud) {
		this.hud = hud;
	}
	
	@SubscribeEvent(receiveCanceled=true)
	public void onEvent(RenderGameOverlayEvent.Pre event) {

		switch(event.getType()) {
			case HOTBAR:
				hud.render(event.getResolution().getScaledWidth(), event.getResolution().getScaledHeight());
				break;
			default:
				break;
		}
		
	}
	
}
