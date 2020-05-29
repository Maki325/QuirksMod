package com.crimzonmodz.bokunoheroacademia.setup;

import com.crimzonmodz.bokunoheroacademia.handlers.PlayerEventHandler;
import net.minecraftforge.common.MinecraftForge;

public class ServerProxy implements IProxy {

    @Override
    public void init() {
        MinecraftForge.EVENT_BUS.register(PlayerEventHandler.class);
    }

}
