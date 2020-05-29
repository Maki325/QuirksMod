package com.crimzonmodz.bokunoheroacademia.api.quirk;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public interface Tickable {

    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent event);

}
