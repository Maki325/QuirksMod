package me.maki325.bokunoheroacademia.network;

import me.maki325.bokunoheroacademia.BnHA;
import me.maki325.bokunoheroacademia.network.packates.ActivateQuirkPacket;
import me.maki325.bokunoheroacademia.network.packates.SyncQuirkWithClient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Networking {

    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextID() { return ID++; }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(BnHA.MODID, BnHA.MODID), () -> "1.0", s -> true, s -> true);

        /*INSTANCE.registerMessage(
                nextID(),
                PacketFunction.class,
                PacketFunction::toBytes,
                PacketFunction::new,
                PacketFunction::handle
        );*/
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
