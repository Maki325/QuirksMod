package com.crimzonmodz.bokunoheroacademia.network;

import com.crimzonmodz.bokunoheroacademia.BnHA;
import com.crimzonmodz.bokunoheroacademia.network.packets.ActivateQuirkPacket;
import com.crimzonmodz.bokunoheroacademia.network.packets.SyncQuirkWithClient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Networking {

    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextID() { return ID++; }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(BnHA.MODID, BnHA.MODID), () -> "1.0", s -> true, s -> true);

        INSTANCE.registerMessage(
            nextID(),
            PacketFunction.class,
            PacketFunction::toBytes,
            PacketFunction::new,
            PacketFunction::handle
        );
        INSTANCE.registerMessage(
            nextID(),
            ActivateQuirkPacket.class,
            ActivateQuirkPacket::toBytes,
            ActivateQuirkPacket::new,
            ActivateQuirkPacket::handle
        );
        INSTANCE.registerMessage(
            nextID(),
            SyncQuirkWithClient.class,
            SyncQuirkWithClient::toBytes,
            SyncQuirkWithClient::new,
            SyncQuirkWithClient::handle
        );
    }

}
