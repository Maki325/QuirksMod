package me.maki325.bokunoheroacademia;

import me.maki325.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import me.maki325.bokunoheroacademia.network.Networking;
import me.maki325.bokunoheroacademia.network.packates.SyncQuirkWithClient;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkDirection;

import javax.annotation.Nonnull;

public class Helper {

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
