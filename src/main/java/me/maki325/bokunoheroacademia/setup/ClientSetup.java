package me.maki325.bokunoheroacademia.setup;

import me.maki325.bokunoheroacademia.BnHA;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import me.maki325.bokunoheroacademia.network.Networking;
import me.maki325.bokunoheroacademia.network.packates.ActivateQuirkPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BnHA.MODID)
public class ClientSetup {

    public static void init(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(ClientSetup.class);

        KeyBindings.init();
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onKeyInput(TickEvent.ClientTickEvent event) {
        if(KeyBindings.activate.isPressed()) {
            LazyOptional<IQuirk> lazyOptionalClient = Minecraft.getInstance().player.getCapability(QuirkProvider.QUIRK_CAP);
            IQuirk iquirkClient = lazyOptionalClient.orElse(null);
            if(iquirkClient != null && !iquirkClient.getQuirks().isEmpty()) {
                Quirk quirkClient = iquirkClient.getQuirks().get(0);
                if (quirkClient != null || quirkClient.isErased()) {
                    quirkClient.onUse(Minecraft.getInstance().player);
                }
            }

            Networking.INSTANCE.sendToServer(new ActivateQuirkPacket());
        }
    }

}
