package maki325.bnha.init;

import org.lwjgl.input.Keyboard;

import maki325.bnha.util.Reference;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBindings {

	public static KeyBinding activate, test;
	
	public static void init() {
		activate = new KeyBinding("key.activate", Keyboard.KEY_Y, "key.categories." + Reference.MOD_ID);
		test = new KeyBinding("key.test", Keyboard.KEY_9, "key.categories." + Reference.MOD_ID);
		
		ClientRegistry.registerKeyBinding(activate);
		ClientRegistry.registerKeyBinding(test);
	}
	
}
