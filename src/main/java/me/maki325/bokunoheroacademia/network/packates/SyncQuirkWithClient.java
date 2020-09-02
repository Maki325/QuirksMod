package me.maki325.bokunoheroacademia.network.packates;

import io.netty.buffer.ByteBuf;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import me.maki325.bokunoheroacademia.api.quirk.QuirkRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class SyncQuirkWithClient implements IMessage {

    private boolean thisPlayer;
    private UUID playerId;
    private NBTTagCompound data;

    public SyncQuirkWithClient() {}

    public SyncQuirkWithClient(UUID playerId, NBTTagCompound data) {
        this(playerId, data, false);
    }

    public SyncQuirkWithClient(UUID playerId, NBTTagCompound data, boolean thisPlayer) {
        this.playerId = playerId;
        this.data = data;
        this.thisPlayer = thisPlayer;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(thisPlayer);
        buf.writeUniqueId(playerId);
        buf.writeCompoundTag(data);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        NBTTagCompound tag = ByteBufUtils.readTag(buf);

        thisPlayer = tag.getBoolean("thisPlayer");
        playerId = tag.getUniqueId("playerId");
        data = tag.getCompoundTag("data");
    }

    @Override
    public void toBytes(ByteBuf buf) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("thisPlayer", thisPlayer);
        tag.setUniqueId("playerId", playerId);
        tag.setTag("data", data);
        ByteBufUtils.writeTag(buf, tag);
    }

    public static IMessage handle(SyncQuirkWithClient message, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            if(message.thisPlayer) handleThisPlayer(message);
            handleOtherPlayer(message);
        });

        return null;
    }

    private static void handleThisPlayer(SyncQuirkWithClient message) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        IQuirk iq = player.getCapability(QuirkProvider.QUIRK_CAP, null);
        if(iq == null) return;

        Quirk q = QuirkRegistry.get(message.data.getString("quirkName"));
        if(q == null) {
            player.sendChatMessage("No quirk with name " + message.data.getString("quirkName"));
            return;
        }
        q.load(message.data.getCompoundTag("quirkData"));
        // TODO: Do I need this?
        // iq.addQuirks(q);

        player.sendChatMessage("SUCCESSFULL");
    }

    private static void handleOtherPlayer(SyncQuirkWithClient message) {
        EntityPlayerSP player = (EntityPlayerSP) Minecraft.getMinecraft().world.getPlayerEntityByUUID(message.playerId);
        IQuirk iq = player.getCapability(QuirkProvider.QUIRK_CAP, null);
        if(iq == null) return;

        Quirk q = iq.getQuirk(new ResourceLocation(message.data.getString("quirkName")));
        if(q == null) {
            q = QuirkRegistry.get(message.data.getString("quirkName"));
            if(q == null) {
                player.sendChatMessage("No quirk with name " + message.data.getString("quirkName"));
                return;
            }
        }
        q.load(message.data.getCompoundTag("quirkData"));
        // TODO: Do I need this?
        // iq.addQuirks(q);

        player.sendChatMessage("SUCCESSFULL");
    }

}
