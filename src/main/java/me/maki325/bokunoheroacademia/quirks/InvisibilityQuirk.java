package me.maki325.bokunoheroacademia.quirks;

import me.maki325.bokunoheroacademia.BnHA;
import me.maki325.bokunoheroacademia.Helper;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class InvisibilityQuirk extends Quirk {

    public static final ResourceLocation ID = new ResourceLocation(BnHA.MODID, "invisibility");

    public boolean active = false;

    public InvisibilityQuirk() {
        super(ID);
    }

    @Override public void onUse(ServerPlayerEntity player) {
        if(active) {
            active = false;
            player.removePotionEffect(Effects.INVISIBILITY);
            player.sendMessage(new StringTextComponent("Invisibility turned off!"), player.getUniqueID());
        } else {
            active = true;
            player.addPotionEffect(new EffectInstance(Effects.INVISIBILITY, 99999, 0, false, false));
            player.sendMessage(new StringTextComponent("Invisibility turned on!"), player.getUniqueID());
        }
        player.sendPlayerAbilities();
        Helper.syncQuirkWithClient(this, player, true);
    }

    @OnlyIn(Dist.CLIENT)
    @Override public void onUse(ClientPlayerEntity player) {}

    @Override public CompoundNBT save() {
        CompoundNBT out = new CompoundNBT();
        out.putBoolean("active", active);
        return out;
    }

    @Override public void load(CompoundNBT in) {
        active = in.getBoolean("active");
    }

    @SubscribeEvent
    public void onPotionRemove(PotionEvent.PotionRemoveEvent event) {
        if(event.getPotion().equals(Effects.INVISIBILITY) && active)
            event.setCanceled(true);
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        event.getPlayer().sendMessage(new StringTextComponent("Invisibility Quirk"), event.getPlayer().getUniqueID());
    }

}
