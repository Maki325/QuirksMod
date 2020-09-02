package me.maki325.bokunoheroacademia.setup;

import me.maki325.bokunoheroacademia.BnHA;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyBindings {

    public static final String CATEGORY = "key.categories." + BnHA.MODID;

    public static KeyBinding activate, zoomIn, zoomOut;

    public static void init() {
        activate = new KeyBinding("key.activate", Keyboard.KEY_Y, CATEGORY);
        zoomIn = new KeyBinding("key.zoomIn", Keyboard.KEY_ADD, CATEGORY);
        zoomOut = new KeyBinding("key.zoomOut", Keyboard.KEY_SUBTRACT, CATEGORY);

        ClientRegistry.registerKeyBinding(activate);
        ClientRegistry.registerKeyBinding(zoomIn);
        ClientRegistry.registerKeyBinding(zoomOut);
    }

}
