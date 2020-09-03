package me.maki325.bokunoheroacademia;

import me.maki325.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import me.maki325.bokunoheroacademia.network.Networking;
import me.maki325.bokunoheroacademia.network.packates.SyncQuirkWithClient;
import me.maki325.bokunoheroacademia.network.packates.SyncQuirkWithServer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkDirection;

import javax.annotation.Nonnull;

public class Helper {

    public static void syncQuirkWithClient(@Nonnull Quirk q, @Nonnull ServerPlayerEntity serverPlayerEntity) {
        syncQuirkWithClient(q, serverPlayerEntity, false);
    }

    public static void syncQuirkWithClient(@Nonnull Quirk q, @Nonnull ServerPlayerEntity serverPlayerEntity, boolean back) {
        CompoundNBT data = new CompoundNBT();
        INBT nbt = q.save();
        if(nbt == null) nbt = new CompoundNBT();
        data.put("quirkData", nbt);
        data.putBoolean("erased", q.isErased());
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
                CompoundNBT out = quirk.save();
                if(out == null) out = new CompoundNBT();
                d.put("quirkData", out);
                d.putBoolean("erased", quirk.isErased());
                d.putString("quirkName", quirk.getId().toString());

                Networking.INSTANCE.sendTo(new SyncQuirkWithClient(player.getUniqueID(), d), serverPlayerEntity.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
            }
        });
        Networking.INSTANCE.sendTo(new SyncQuirkWithClient(serverPlayerEntity.getUniqueID(), data, true), serverPlayerEntity.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void syncQuirkWithServer(@Nonnull Quirk q) {
        CompoundNBT data = new CompoundNBT();
        INBT nbt = q.save();
        if(nbt == null) nbt = new CompoundNBT();
        data.put("quirkData", nbt);
        data.putString("quirkName", q.getId().toString());
        data.putBoolean("erased", q.isErased());

        Networking.INSTANCE.sendToServer(new SyncQuirkWithServer(data));
    }

}
