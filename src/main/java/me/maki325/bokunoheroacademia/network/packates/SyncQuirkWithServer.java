package me.maki325.bokunoheroacademia.network.packates;

import io.netty.buffer.ByteBuf;
import me.maki325.bokunoheroacademia.Helper;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import me.maki325.bokunoheroacademia.api.quirk.QuirkRegistry;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SyncQuirkWithServer implements IMessage {

    private NBTTagCompound data;

    public SyncQuirkWithServer() {}

    public SyncQuirkWithServer(NBTTagCompound data) {
        this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, data);
    }

    public static IMessage handle(SyncQuirkWithServer message, MessageContext ctx) {
        ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
            EntityPlayerMP player = ctx.getServerHandler().player;
            if(player == null) return;

            IQuirk iq = player.getCapability(QuirkProvider.QUIRK_CAP, null);
            if(iq == null) return;

            Quirk q = iq.getQuirk(new ResourceLocation(message.data.getString("quirkName")));
            if(q == null) {
                q = QuirkRegistry.get(message.data.getString("quirkName"));
                if(q == null) {
                    player.sendMessage(new TextComponentString("No quirk with name " + message.data.getString("quirkName")));
                    return;
                }
            }
            q.load(message.data.getCompoundTag("quirkData"));
            // TODO: Do I need this?
            // iq.addQuirks(q);

            Helper.syncQuirkWithClient(q, player, true);

            player.sendMessage(new TextComponentString("SUCCESSFULL"));
        });

        return null;
    }

}
