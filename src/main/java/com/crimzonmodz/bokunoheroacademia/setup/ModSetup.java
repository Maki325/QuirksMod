package com.crimzonmodz.bokunoheroacademia.setup;

import com.crimzonmodz.bokunoheroacademia.api.capabilities.quirk.CapabilityQuirk;
import com.crimzonmodz.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import com.crimzonmodz.bokunoheroacademia.api.capabilities.quirk.QuirkStorage;
import com.crimzonmodz.bokunoheroacademia.handlers.CapabilityHandler;
import com.crimzonmodz.bokunoheroacademia.network.Networking;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ModSetup {

    public void init() {
        MinecraftForge.EVENT_BUS.register(CapabilityHandler.class);
        MinecraftForge.EVENT_BUS.register(this);
        CapabilityManager.INSTANCE.register(IQuirk.class, new QuirkStorage(), () -> new CapabilityQuirk());

        Networking.registerMessages();
    }

}
