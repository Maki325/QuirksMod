package me.maki325.bokunoheroacademia.api.capabilities.quirk;

import me.maki325.bokunoheroacademia.BnHA;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CapabilityHandler {

    public static final ResourceLocation QUIRK_CAP = new ResourceLocation(BnHA.MODID, "quirk");

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof PlayerEntity)) return;

        event.addCapability(QUIRK_CAP, new QuirkProvider());
    }

}
