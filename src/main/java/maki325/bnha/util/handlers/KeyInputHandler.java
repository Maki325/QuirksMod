package maki325.bnha.util.handlers;

import maki325.bnha.BnHA;
import maki325.bnha.init.KeyBindings;
import maki325.bnha.init.ModQuirks;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyInputHandler {

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if(KeyBindings.activate.isPressed()){
			System.out.println("KEY F");
			String quirk = BnHA.quirks;
			
			System.out.println("QUIRK NAME: " + quirk);
			if(quirk != "none") {
				ModQuirks.useByName(quirk, Minecraft.getMinecraft().player);
				System.out.println("QUIRK ACTIVATED: " + quirk);
			}
		}
	}
	
}
