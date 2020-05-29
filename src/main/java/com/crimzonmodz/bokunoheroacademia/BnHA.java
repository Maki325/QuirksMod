package com.crimzonmodz.bokunoheroacademia;

import com.crimzonmodz.bokunoheroacademia.api.quirk.QuirkRegistry;
import com.crimzonmodz.bokunoheroacademia.quirks.fly.QuirkFly;
import com.crimzonmodz.bokunoheroacademia.setup.ClientProxy;
import com.crimzonmodz.bokunoheroacademia.setup.IProxy;
import com.crimzonmodz.bokunoheroacademia.setup.ModSetup;
import com.crimzonmodz.bokunoheroacademia.setup.ServerProxy;
import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BnHA.MODID)
public class BnHA {

    public static final String MODID = "bokunoheroacademia";

    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    public static ModSetup setup = new ModSetup();

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public BnHA() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        addQuirks();

        MinecraftForge.EVENT_BUS.register(BnHA.class);

        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("bokunoheroacademia-client.toml"));
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("bokunoheroacademia-common.toml"));
    }

    private void addQuirks() {
        QuirkRegistry.addQuirk(QuirkFly.ID, QuirkFly.class);
        /**
         * TODO: I can't see my power on
         */
        /*QuirkRegistry.addQuirk(new TestQuirk());
        QuirkRegistry.addQuirk(new QuirkExplosion());
        QuirkRegistry.addQuirk(new QuirkHellflame());*/
    }

    private void setup(final FMLCommonSetupEvent event) {
        setup.init();
        proxy.init();
    }


    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }

}
