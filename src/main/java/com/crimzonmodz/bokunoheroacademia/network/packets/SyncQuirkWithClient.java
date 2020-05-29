package com.crimzonmodz.bokunoheroacademia.network.packets;

import com.crimzonmodz.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import com.crimzonmodz.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import com.crimzonmodz.bokunoheroacademia.api.quirk.Quirk;
import com.crimzonmodz.bokunoheroacademia.api.quirk.QuirkRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class SyncQuirkWithClient {

    private boolean thisPlayer;
    private UUID playerId;
    private CompoundNBT data;

    public SyncQuirkWithClient(PacketBuffer buf) {
        thisPlayer = buf.readBoolean();
        playerId = buf.readUniqueId();
        data = buf.readCompoundTag();
    }

    public SyncQuirkWithClient(UUID playerId, CompoundNBT data) {
        this(playerId, data, false);
    }

    public SyncQuirkWithClient(UUID playerId, CompoundNBT data, boolean thisPlayer) {
        this.playerId = playerId;
        this.data = data;
        this.thisPlayer = thisPlayer;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(thisPlayer);
        buf.writeUniqueId(playerId);
        buf.writeCompoundTag(data);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(thisPlayer) handleThisPlayer();
            handleOtherPlayer();
        });
        ctx.get().setPacketHandled(true);
    }

    private void handleThisPlayer() {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        LazyOptional<IQuirk> lo = player.getCapability(QuirkProvider.QUIRK_CAP);
        IQuirk iq = lo.orElse(null);
        if(iq == null) return;

        Quirk q = QuirkRegistry.getQuirk(data.getString("quirkName"));
        if(q == null) {
            player.sendMessage(new StringTextComponent("No quirk with name " + data.getString("quirkName")));
            return;
        }
        q.load(data.getCompound("quirkData"));
        iq.addQuirks(q);

        player.sendMessage(new StringTextComponent("SUCCESSFULL"));
    }

    private void handleOtherPlayer() {
        PlayerEntity player = Minecraft.getInstance().world.getPlayerByUuid(playerId);
        LazyOptional<IQuirk> lo = player.getCapability(QuirkProvider.QUIRK_CAP);
        IQuirk iq = lo.orElse(null);
        if(iq == null) return;

        Quirk q = iq.getQuirk(new ResourceLocation(data.getString("quirkName")));
        if(q == null) {
            q = QuirkRegistry.getQuirk(data.getString("quirkName"));
            if(q == null) {
                player.sendMessage(new StringTextComponent("No quirk with name " + data.getString("quirkName")));
                return;
            }
        }
        q.load(data.getCompound("quirkData"));
        iq.addQuirks(q);

        player.sendMessage(new StringTextComponent("SUCCESSFULL"));
    }

}
