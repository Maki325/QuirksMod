package me.maki325.bokunoheroacademia.handlers;

import me.maki325.bokunoheroacademia.Helper;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import me.maki325.bokunoheroacademia.quirks.EraserQuirk;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerEventHandler {

    @SubscribeEvent public static void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        LazyOptional<IQuirk> lazyOptional = player.getCapability(QuirkProvider.QUIRK_CAP);
        IQuirk iquirk = lazyOptional.orElse(null);
        if(iquirk == null) return;
        if(iquirk.getQuirks() == null) return;

        //if(!iquirk.getQuirks().isEmpty() && !(iquirk.getQuirk(0) instanceof ZoomQuirk)) iquirk.getQuirks().clear();
        if(iquirk.getQuirks().isEmpty() || iquirk.getQuirks().get(0) == null) {
            iquirk.addQuirks(new EraserQuirk());
            player.sendMessage(new StringTextComponent("NEW"), player.getUniqueID());
        }
        Quirk quirk = iquirk.getQuirks().get(0);
        MinecraftForge.EVENT_BUS.register(quirk);
        Helper.syncQuirkWithClient(quirk, player, true);
    }

    @SubscribeEvent public static void playerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        LazyOptional<IQuirk> lazyOptional = player.getCapability(QuirkProvider.QUIRK_CAP);
        IQuirk iquirk = lazyOptional.orElse(null);
        if(iquirk == null) return;
        if(iquirk.getQuirks() == null || iquirk.getQuirks().get(0) == null) return;
        Quirk quirk = iquirk.getQuirks().get(0);
        MinecraftForge.EVENT_BUS.unregister(quirk);
        Helper.syncQuirkWithClient(quirk, player, true);
    }

}
