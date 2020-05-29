package com.crimzonmodz.bokunoheroacademia.setup;

import com.crimzonmodz.bokunoheroacademia.KeyBindings;
import com.crimzonmodz.bokunoheroacademia.api.QuirkLayer;
import com.crimzonmodz.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import com.crimzonmodz.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import com.crimzonmodz.bokunoheroacademia.api.quirk.Quirk;
import com.crimzonmodz.bokunoheroacademia.network.Networking;
import com.crimzonmodz.bokunoheroacademia.network.packets.ActivateQuirkPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientProxy implements IProxy {

    @Override
    public void init() {
        KeyBindings.init();
        MinecraftForge.EVENT_BUS.register(ClientProxy.class);

        EntityRendererManager manager = Minecraft.getInstance().getRenderManager();
        manager.getSkinMap().get("default").addLayer(new QuirkLayer(manager.getSkinMap().get("default")));
        manager.getSkinMap().get("slim").addLayer(new QuirkLayer(manager.getSkinMap().get("slim")));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onKeyInput(TickEvent.ClientTickEvent event) {
        if(KeyBindings.activate.isPressed()) {
            LazyOptional<IQuirk> lazyOptionalClient = Minecraft.getInstance().player.getCapability(QuirkProvider.QUIRK_CAP);
            IQuirk iquirkClient = lazyOptionalClient.orElse(null);
            if(iquirkClient != null && !iquirkClient.getQuirks().isEmpty()) {
                Quirk quirkClient = iquirkClient.getQuirks().get(0);
                if (quirkClient != null) {
                    quirkClient.onClientUse(Minecraft.getInstance().player, Minecraft.getInstance().world);
                }
            }

            Networking.INSTANCE.sendToServer(new ActivateQuirkPacket());
        }
    }

    /*
    @SubscribeEvent public static void onEntityConstruction(EntityEvent.EntityConstructing event) {
    }*/

}
