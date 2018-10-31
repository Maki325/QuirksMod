package maki325.bnha.util;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBindings {

	public static KeyBinding activate, skilltree;
	
	public static void init() {
		activate = new KeyBinding("key.activate", Keyboard.KEY_Y, "key.categories." + Reference.MOD_ID);
		skilltree = new KeyBinding("key.skilltree", Keyboard.KEY_V, "key.categories." + Reference.MOD_ID);
		
		ClientRegistry.registerKeyBinding(activate);
		ClientRegistry.registerKeyBinding(skilltree);
	}
	
}
