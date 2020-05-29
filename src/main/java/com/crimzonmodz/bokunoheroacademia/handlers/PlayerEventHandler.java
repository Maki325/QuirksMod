package com.crimzonmodz.bokunoheroacademia.handlers;

import com.crimzonmodz.bokunoheroacademia.Helper;
import com.crimzonmodz.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import com.crimzonmodz.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import com.crimzonmodz.bokunoheroacademia.api.event.QuirkEvent;
import com.crimzonmodz.bokunoheroacademia.api.quirk.Quirk;
import com.crimzonmodz.bokunoheroacademia.network.Networking;
import com.crimzonmodz.bokunoheroacademia.network.packets.SyncQuirkWithClient;
import com.crimzonmodz.bokunoheroacademia.quirks.fly.QuirkFly;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.NetworkDirection;

public class PlayerEventHandler {

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        System.out.println("onPlayerJoin");
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        LazyOptional<IQuirk> lazyOptional = player.getCapability(QuirkProvider.QUIRK_CAP);
        IQuirk iquirk = lazyOptional.orElse(null);
        if(iquirk == null) return;
        if(iquirk.getQuirks() == null) return;

        if(iquirk.getQuirks().isEmpty() || iquirk.getQuirks().get(0) == null) {
            iquirk.addQuirks(new QuirkFly());
            player.sendMessage(new StringTextComponent("NEW"));
        }
        Quirk quirk = iquirk.getQuirks().get(0);

        MinecraftForge.EVENT_BUS.register(quirk);
        Helper.syncQuirkWithClient(quirk, player, true);

        quirk.onPlayerJoin(player);

        MinecraftForge.EVENT_BUS.post(new QuirkEvent.Change(player,null, quirk));
    }

    @SubscribeEvent
    public static void onQuirkChange(QuirkEvent.Change event) {
        System.out.println("onQuirkChange");
        PlayerEntity player = event.getPlayer();
        Quirk before = event.getBefore(), after = event.getAfter();
        System.out.println("Player: " + player);
        System.out.println("Before: " + before);
        System.out.println("After: " + after);

        player.sendMessage(new StringTextComponent("onQuirkChange"));
    }

    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        PlayerEntity player = event.getPlayer();
        LazyOptional<IQuirk> lazyOptional = player.getCapability(QuirkProvider.QUIRK_CAP);
        IQuirk iquirk = lazyOptional.orElse(null);
        if(iquirk == null) return;
        for(Quirk q : iquirk.getQuirks()) {
            MinecraftForge.EVENT_BUS.unregister(q);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if(!event.isWasDeath()) return;
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();

        LazyOptional<IQuirk> lazyOptional = player.getCapability(QuirkProvider.QUIRK_CAP);
        IQuirk qurik = lazyOptional.orElse(null);
        if(qurik == null) return;

        LazyOptional<IQuirk> lazyOptionalOriginal = event.getOriginal().getCapability(QuirkProvider.QUIRK_CAP);
        IQuirk oldQuirk = lazyOptionalOriginal.orElse(null);
        if(oldQuirk == null) return;

        if(!oldQuirk.getQuirks().isEmpty())
            MinecraftForge.EVENT_BUS.unregister(oldQuirk.getQuirks().get(0));

        oldQuirk.getQuirks().forEach(q -> {
            qurik.addQuirks(q);
            q.onPlayerDeath(player);
            Helper.syncQuirkWithClient(q, player);
        });

        if(!qurik.getQuirks().isEmpty())
            MinecraftForge.EVENT_BUS.register(qurik.getQuirks().get(0));
    }

    public static void syncClientWithClient(Quirk quirk, ServerPlayerEntity serverPlayerEntity) {
        serverPlayerEntity.getServerWorld().getPlayers().forEach(player -> {
            if(player.getUniqueID() == serverPlayerEntity.getUniqueID()) return;
            LazyOptional<IQuirk> lo = player.getCapability(QuirkProvider.QUIRK_CAP);
            IQuirk iq = lo.orElse(null);
            if(iq == null && iq.getQuirk(0) != null) return;
            CompoundNBT quirkData = new CompoundNBT();
            quirkData.put("quirkData", iq.getQuirk(0).save());
            quirkData.putString("quirkName", iq.getQuirk(0).getId().toString());

            Networking.INSTANCE.sendTo(new SyncQuirkWithClient(player.getUniqueID(), quirkData), serverPlayerEntity.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
        });
        Helper.syncQuirkWithClient(quirk, serverPlayerEntity);

        /*CompoundNBT data = new CompoundNBT();
        data.put("quirkData", quirk.save());
        data.putString("quirkName", quirk.getName());

        serverPlayerEntity.getServerWorld().getPlayers().forEach(player -> {
            Networking.INSTANCE.sendTo(new SyncQuirkWithClient(serverPlayerEntity.getUniqueID(), data), player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
        });
        Networking.INSTANCE.sendTo(new SyncQuirkWithClient(serverPlayerEntity.getUniqueID(), data, true), serverPlayerEntity.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);*/
        /*CompoundNBT map = new CompoundNBT();
        serverPlayerEntity.getServerWorld().getPlayers().forEach(player -> {
            LazyOptional<IQuirk> lo = player.getCapability(QuirkProvider.QUIRK_CAP);
            IQuirk iq = lo.orElse(null);
            if(iq == null && iq.getQuirk(0) != null) return;
            CompoundNBT quirkData = new CompoundNBT();
            quirkData.put("data", iq.getQuirk(0).save());
            quirkData.putString("id", iq.getQuirk(0).getId().toString());
            map.put(player.getUniqueID().toString(), quirkData);
        });

        serverPlayerEntity.getServerWorld().getPlayers().forEach(player -> {
            Networking.INSTANCE.sendTo(new PacketFunction((world, z, mapData) -> {
                try {
                    mapData.keySet().forEach((key) -> {
                        if(z.getUniqueID().toString() == key) return;
                        UUID uuid = UUID.fromString(key);
                        CompoundNBT quirkData = mapData.getCompound(key);

                        PlayerEntity playerEntity = world.getPlayerByUuid(uuid);
                        LazyOptional<IQuirk> lo = playerEntity.getCapability(QuirkProvider.QUIRK_CAP);
                        IQuirk iq = lo.orElse(null);
                        if(iq == null) return;
                        iq.clear();


                        Quirk q = QuirkRegistry.getQuirk(quirkData.getString("id"));
                        if(q == null) {
                            return;
                        }
                        q.load(quirkData.getCompound("data"));
                        iq.addQuirks(q);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, map), player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
        });

        CompoundNBT prepareData = new CompoundNBT();
        prepareData.putString("quirkName", quirk.getId().toString());
        prepareData.put("quirkData", quirk.save());
        Networking.INSTANCE.sendTo(new PacketFunction((world, p, data) -> {
            try {
                LazyOptional<IQuirk> lo = p.getCapability(QuirkProvider.QUIRK_CAP);
                IQuirk iq = lo.orElse(null);
                if(iq == null) return;

                Quirk q = QuirkRegistry.getQuirk(data.getString("quirkName"));
                if(q == null) {
                    return;
                }
                q.load(data.getCompound("quirkData"));
                iq.addQuirks(q);

                p.sendMessage(new StringTextComponent("SUCCESSFULL"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, prepareData), serverPlayerEntity.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);*/
    }

}
