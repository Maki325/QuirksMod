package com.crimzonmodz.bokunoheroacademia;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

    public static KeyBinding activate, test, next, previous;

    public static void init() {
        activate = new KeyBinding("key.activate", GLFW.GLFW_KEY_Z, "key.categories." + BnHA.MODID);
        test = new KeyBinding("key.test", GLFW.GLFW_KEY_V, "key.categories." + BnHA.MODID);

        previous = new KeyBinding("key.previous", GLFW.GLFW_KEY_X, "key.categories." + BnHA.MODID);
        next = new KeyBinding("key.next", GLFW.GLFW_KEY_C, "key.categories." + BnHA.MODID);

        ClientRegistry.registerKeyBinding(activate);
        ClientRegistry.registerKeyBinding(test);
        ClientRegistry.registerKeyBinding(next);
        ClientRegistry.registerKeyBinding(previous);
    }

}
