package me.maki325.bokunoheroacademia.setup;

import me.maki325.bokunoheroacademia.BnHA;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

    public static KeyBinding activate;

    public static void init() {
        activate = new KeyBinding("key.activate", GLFW.GLFW_KEY_Z, "key.categories." + BnHA.MODID);

        ClientRegistry.registerKeyBinding(activate);
    }

}
