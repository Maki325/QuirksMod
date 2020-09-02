package me.maki325.bokunoheroacademia.network;

import me.maki325.bokunoheroacademia.BnHA;
import me.maki325.bokunoheroacademia.network.packates.ActivateQuirkPacket;
import me.maki325.bokunoheroacademia.network.packates.SyncQuirkWithClient;
import me.maki325.bokunoheroacademia.network.packates.SyncQuirkWithServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class Networking {

    public static SimpleNetworkWrapper INSTANCE;
    private static int ID = 0;

    public static int nextID() { return ID++; }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(BnHA.MODID);

        INSTANCE.registerMessage(ActivateQuirkPacket::handle, ActivateQuirkPacket.class, nextID(), Side.SERVER);
        INSTANCE.registerMessage(SyncQuirkWithClient::handle, SyncQuirkWithClient.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(SyncQuirkWithServer::handle, SyncQuirkWithServer.class, nextID(), Side.CLIENT);
    }

}
