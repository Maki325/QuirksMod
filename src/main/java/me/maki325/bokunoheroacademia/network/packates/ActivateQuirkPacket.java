package me.maki325.bokunoheroacademia.network.packates;

import me.maki325.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ActivateQuirkPacket {

    public ActivateQuirkPacket(PacketBuffer buf) {}
    public ActivateQuirkPacket() {}

    public void toBytes(PacketBuffer buf) {}

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if(player == null) return;

            LazyOptional<IQuirk> lazyOptional = player.getCapability(QuirkProvider.QUIRK_CAP);
            IQuirk iquirk = lazyOptional.orElse(null);
            if(iquirk == null || iquirk.getQuirks().isEmpty()) return;
            Quirk q = iquirk.getQuirks().get(0);
            if(q == null) return;
            q.onUse(player);
        });
        ctx.get().setPacketHandled(true);
    }

}
