package me.maki325.bokunoheroacademia.handlers;

import me.maki325.bokunoheroacademia.Helper;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import me.maki325.bokunoheroacademia.quirks.TestQuirk;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerEventHandler {

    @SubscribeEvent public static void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        System.out.println("onPlayerJoin");
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        LazyOptional<IQuirk> lazyOptional = player.getCapability(QuirkProvider.QUIRK_CAP);
        IQuirk iquirk = lazyOptional.orElse(null);
        if(iquirk == null) return;
        if(iquirk.getQuirks() == null) return;

        if(iquirk.getQuirks().isEmpty() || iquirk.getQuirks().get(0) == null) {
            iquirk.addQuirks(new TestQuirk());
            player.sendMessage(new StringTextComponent("NEW"), player.getUniqueID());
        }
        Quirk quirk = iquirk.getQuirks().get(0);
        MinecraftForge.EVENT_BUS.register(quirk);
        Helper.syncQuirkWithClient(quirk, player, true);
        quirk.onPlayerJoin(player);
        //MinecraftForge.EVENT_BUS.post(new QuirkEvent.Change(player,null, quirk));
    }

}
