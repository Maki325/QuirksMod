package me.maki325.bokunoheroacademia;

import me.maki325.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import me.maki325.bokunoheroacademia.network.Networking;
import me.maki325.bokunoheroacademia.network.packates.SyncQuirkWithClient;
import me.maki325.bokunoheroacademia.network.packates.SyncQuirkWithServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

public class Helper {

    public static void syncQuirkWithClient(@Nonnull Quirk q, @Nonnull EntityPlayerMP serverPlayerEntity) {
        syncQuirkWithClient(q, serverPlayerEntity, false);
    }

    public static void syncQuirkWithClient(@Nonnull Quirk q, @Nonnull EntityPlayerMP serverPlayerEntity, boolean back) {
        NBTTagCompound data = new NBTTagCompound();
        NBTTagCompound nbt = q.save();
        if(nbt == null) nbt = new NBTTagCompound();
        data.setTag("quirkData", nbt);
        data.setString("quirkName", q.getId().toString());

        serverPlayerEntity.getServerWorld().playerEntities.forEach(player -> {
            if(player.getUniqueID() == serverPlayerEntity.getUniqueID()) return;
            Networking.INSTANCE.sendTo(new SyncQuirkWithClient(serverPlayerEntity.getUniqueID(), data), (EntityPlayerMP) player);

            if(back) {
                IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
                if(iquirk == null || iquirk.getQuirks() == null || iquirk.getQuirks().isEmpty() || iquirk.getQuirks().get(0) == null) return;

                NBTTagCompound d = new NBTTagCompound();
                Quirk quirk = iquirk.getQuirks().get(0);
                NBTTagCompound out = quirk.save();
                if(out == null) out = new NBTTagCompound();
                d.setTag("quirkData", out);
                d.setString("quirkName", quirk.getId().toString());

                Networking.INSTANCE.sendTo(new SyncQuirkWithClient(player.getUniqueID(), d), serverPlayerEntity);
            }
        });
        Networking.INSTANCE.sendTo(new SyncQuirkWithClient(serverPlayerEntity.getUniqueID(), data, true), serverPlayerEntity);
    }

    public static void syncQuirkWithServer(@Nonnull Quirk q) {
        NBTTagCompound data = new NBTTagCompound();
        NBTTagCompound nbt = q.save();
        if(nbt == null) nbt = new NBTTagCompound();
        data.setTag("quirkData", nbt);
        data.setString("quirkName", q.getId().toString());

        Networking.INSTANCE.sendToServer(new SyncQuirkWithServer(data));
    }

}
