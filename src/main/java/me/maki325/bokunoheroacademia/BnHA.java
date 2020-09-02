package me.maki325.bokunoheroacademia;

import me.maki325.bokunoheroacademia.setup.CommonProxy;
import me.maki325.bokunoheroacademia.setup.ModSetup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(modid = BnHA.MODID, name = BnHA.NAME, version = BnHA.VERSION)
public class BnHA {

    public static final String MODID = "bokunoheroacademia";
    public static final String NAME = "My Hero Academia";
    public static final String VERSION = "1.0";

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String PROXY_LOCATION = "me.maki325.bokunoheroacademia.setup";
    private static final String CLIENT_PROXY = PROXY_LOCATION + ".ClientSetup";
    private static final String COMMON_PROXY = PROXY_LOCATION + ".CommonProxy";

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ModSetup.init();
        proxy.init();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

}
