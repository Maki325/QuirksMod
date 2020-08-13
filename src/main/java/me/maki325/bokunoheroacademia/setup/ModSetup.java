package me.maki325.bokunoheroacademia.setup;

import me.maki325.bokunoheroacademia.BnHA;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.CapabilityHandler;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.CapabilityQuirk;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.QuirkStorage;
import me.maki325.bokunoheroacademia.api.quirk.QuirkRegistry;
import me.maki325.bokunoheroacademia.handlers.PlayerEventHandler;
import me.maki325.bokunoheroacademia.network.Networking;
import me.maki325.bokunoheroacademia.quirks.TestQuirk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = BnHA.MODID)
public class ModSetup {

    public static void init(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IQuirk.class, new QuirkStorage(), () -> new CapabilityQuirk());
        MinecraftForge.EVENT_BUS.register(CapabilityHandler.class);
        MinecraftForge.EVENT_BUS.register(PlayerEventHandler.class);
        QuirkRegistry.addQuirk(TestQuirk.ID, () -> new TestQuirk());

        Networking.registerMessages();
    }

}
