package com.crimzonmodz.bokunoheroacademia.network;

import com.crimzonmodz.bokunoheroacademia.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketFunction {

    private final Function function;
    private final CompoundNBT data;

    public PacketFunction(PacketBuffer buf) {
        this.function = Helper.deserializeFunction(buf.readByteArray());
        this.data = buf.readCompoundTag();
    }
    public PacketFunction(Function function, CompoundNBT data) {
        this.function = function;
        this.data = data;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeByteArray(Helper.serializeFunction(function));
        buf.writeCompoundTag(data);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if(ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)) {
            handleClient(ctx);
        } else {
            handleServer(ctx);
        }
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    public void handleServer(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            World world = ctx.get().getSender().world;
            ServerPlayerEntity player = ctx.get().getSender();
            function.run(world, player, data);
        });
        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleClient(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientWorld world = Minecraft.getInstance().world;
            PlayerEntity player = Minecraft.getInstance().player;
            function.run(world, player, data);
        });
        ctx.get().setPacketHandled(true);
    }

}
