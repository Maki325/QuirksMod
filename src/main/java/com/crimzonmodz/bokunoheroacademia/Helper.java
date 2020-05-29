package com.crimzonmodz.bokunoheroacademia;

import com.crimzonmodz.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import com.crimzonmodz.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import com.crimzonmodz.bokunoheroacademia.api.quirk.Quirk;
import com.crimzonmodz.bokunoheroacademia.network.Function;
import com.crimzonmodz.bokunoheroacademia.network.Networking;
import com.crimzonmodz.bokunoheroacademia.network.PacketFunction;
import com.crimzonmodz.bokunoheroacademia.network.packets.SyncQuirkWithClient;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkDirection;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.HashMap;

public class Helper {

    public static Function deserializeFunction(byte[] function) {
        try {
            ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(function));
            Function fnc = (Function) iStream.readObject();
            iStream.close();
            return fnc;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return (world, player, data) -> { System.err.println("FUNCTION COULDN'T BE DESERIALIZED!!!"); };
        }
    }

    public static byte[] serializeFunction(Function fnc) {
        try {
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(bStream);
            oo.writeObject(fnc);
            oo.close();
            return bStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("FUNCTION COULDN'T BE SERIALIZED!!!");
            return new byte[0];
        }
    }

    public static HashMap deserializeMap(byte[] function) {
        try {
            ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(function));
            HashMap fnc = (HashMap) iStream.readObject();
            iStream.close();
            return fnc;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new HashMap();
        }
    }

    public static byte[] serializeMap(HashMap fnc) {
        try {
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(bStream);
            oo.writeObject(fnc);
            oo.close();
            return bStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("FUNCTION COULDN'T BE SERIALIZED!!!");
            return new byte[0];
        }
    }

    public static void sendToAll(ServerPlayerEntity serverPlayerEntity, PacketFunction packetFunction) {
        sendToAll(serverPlayerEntity, packetFunction, true);
    }

    public static void sendToAll(ServerPlayerEntity serverPlayerEntity, PacketFunction packetFunction, boolean toSender) {
        serverPlayerEntity.getServerWorld().getPlayers().forEach(player -> {
            if(toSender && player.getUniqueID() == serverPlayerEntity.getUniqueID()) return;
            Networking.INSTANCE.sendTo(packetFunction, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
        });
    }

    public static void syncQuirkWithClient(@Nonnull Quirk q, @Nonnull ServerPlayerEntity serverPlayerEntity) {
        syncQuirkWithClient(q, serverPlayerEntity, false);
    }

    public static void syncQuirkWithClient(@Nonnull Quirk q, @Nonnull ServerPlayerEntity serverPlayerEntity, boolean back) {
        CompoundNBT data = new CompoundNBT();
        data.put("quirkData", q.save());
        data.putString("quirkName", q.getId().toString());

        serverPlayerEntity.getServerWorld().getPlayers().forEach(player -> {
            if(player.getUniqueID() == serverPlayerEntity.getUniqueID()) return;
            Networking.INSTANCE.sendTo(new SyncQuirkWithClient(serverPlayerEntity.getUniqueID(), data), player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);

            if(back) {
                LazyOptional<IQuirk> lazyOptional = player.getCapability(QuirkProvider.QUIRK_CAP);
                IQuirk iquirk = lazyOptional.orElse(null);
                if(iquirk == null || iquirk.getQuirks() == null || iquirk.getQuirks().isEmpty() || iquirk.getQuirks().get(0) == null) return;

                CompoundNBT d = new CompoundNBT();
                Quirk quirk = iquirk.getQuirks().get(0);
                d.put("quirkData", quirk.save());
                d.putString("quirkName", quirk.getId().toString());

                Networking.INSTANCE.sendTo(new SyncQuirkWithClient(player.getUniqueID(), d), serverPlayerEntity.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
            }
        });
        Networking.INSTANCE.sendTo(new SyncQuirkWithClient(serverPlayerEntity.getUniqueID(), data, true), serverPlayerEntity.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }

}
