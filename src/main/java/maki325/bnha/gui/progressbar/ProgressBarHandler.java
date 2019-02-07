package maki325.bnha.gui.progressbar;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ProgressBarHandler {

	private GuiProgressBar progressBar;
	
	public ProgressBarHandler(GuiProgressBar progressBar) {
		this.progressBar = progressBar;
	}
	
	@SubscribeEvent(receiveCanceled=true)
	public void onEvent(RenderGameOverlayEvent.Pre event) {

		switch(event.getType()) {
			case HOTBAR:
				progressBar.render(event.getResolution().getScaledWidth(), event.getResolution().getScaledHeight());
				break;
			default:
				break;
		}
		
	}
	
}
