package me.maki325.bokunoheroacademia.network.packates;

import io.netty.buffer.ByteBuf;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ActivateQuirkPacket implements IMessage {

    public ActivateQuirkPacket() {}

    public void toBytes(PacketBuffer buf) {}

    @Override public void fromBytes(ByteBuf buf) {}

    @Override public void toBytes(ByteBuf buf) {}

    public static IMessage handle(ActivateQuirkPacket message, MessageContext ctx) {
        ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
            EntityPlayerMP player = ctx.getServerHandler().player;
            if(player == null) return;

            IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
            if(iquirk == null || iquirk.getQuirks().isEmpty()) return;
            Quirk q = iquirk.getQuirks().get(0);
            if(q == null || q.isErased()) return;
            q.onUse(player);
        });

        return null;
    }

}
