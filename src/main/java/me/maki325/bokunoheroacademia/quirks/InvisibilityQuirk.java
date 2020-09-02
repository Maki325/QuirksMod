package me.maki325.bokunoheroacademia.quirks;

import me.maki325.bokunoheroacademia.BnHA;
import me.maki325.bokunoheroacademia.Helper;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class InvisibilityQuirk extends Quirk {

    public static final ResourceLocation ID = new ResourceLocation(BnHA.MODID, "invisibility");

    public boolean active = false;

    private static final Potion INVISIBILITY = Potion.getPotionFromResourceLocation("invisibility");

    public InvisibilityQuirk() {
        super(ID);
    }

    @Override public void onUse(EntityPlayerMP player) {
        if(active) {
            active = false;
            player.removePotionEffect(INVISIBILITY);
            player.sendMessage(new TextComponentString("Invisibility turned off!"));
        } else {
            active = true;
            player.addPotionEffect(new PotionEffect(INVISIBILITY, 99999, 0, false, false));
            player.sendMessage(new TextComponentString("Invisibility turned on!"));
        }
        player.sendPlayerAbilities();
        Helper.syncQuirkWithClient(this, player, true);
    }

    @Override public void onUse(EntityPlayerSP player) {}

    @Override public NBTTagCompound save() {
        NBTTagCompound out = new NBTTagCompound();
        out.setBoolean("active", active);
        return out;
    }

    @Override public void load(NBTTagCompound in) {
        active = in.getBoolean("active");
    }

    @SubscribeEvent
    public void onPotionRemove(PotionEvent.PotionRemoveEvent event) {
        if(event.getPotion().equals(INVISIBILITY) && active)
            event.setCanceled(true);
    }

    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        event.player.sendMessage(new TextComponentString("Invisibility Quirk"));
    }

}
