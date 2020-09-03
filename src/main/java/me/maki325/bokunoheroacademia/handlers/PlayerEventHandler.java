package me.maki325.bokunoheroacademia.handlers;

import me.maki325.bokunoheroacademia.Helper;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import me.maki325.bokunoheroacademia.quirks.EraserQuirk;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class PlayerEventHandler {

    @SubscribeEvent public static void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayerMP player = (EntityPlayerMP) event.player;
        IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
        if(iquirk == null) return;
        if(iquirk.getQuirks() == null) return;

        //if(!iquirk.getQuirks().isEmpty() && !(iquirk.getQuirk(0) instanceof ZoomQuirk)) iquirk.getQuirks().clear();
        if(iquirk.getQuirks().isEmpty() || iquirk.getQuirks().get(0) == null) {
            iquirk.addQuirks(new EraserQuirk());
            player.sendMessage(new TextComponentString("NEW"));
        }
        Quirk quirk = iquirk.getQuirks().get(0);
        MinecraftForge.EVENT_BUS.register(quirk);
        Helper.syncQuirkWithClient(quirk, player, true);
    }

    @SubscribeEvent public static void playerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        EntityPlayerMP player = (EntityPlayerMP) event.player;
        IQuirk iquirk = player.getCapability(QuirkProvider.QUIRK_CAP, null);
        if(iquirk == null) return;
        if(iquirk.getQuirks() == null || iquirk.getQuirks().get(0) == null) return;
        Quirk quirk = iquirk.getQuirks().get(0);
        MinecraftForge.EVENT_BUS.unregister(quirk);
        Helper.syncQuirkWithClient(quirk, player, true);
    }

}
