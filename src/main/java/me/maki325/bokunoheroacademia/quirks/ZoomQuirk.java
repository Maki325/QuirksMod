package me.maki325.bokunoheroacademia.quirks;

import me.maki325.bokunoheroacademia.BnHA;
import me.maki325.bokunoheroacademia.Helper;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import me.maki325.bokunoheroacademia.setup.KeyBindings;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ZoomQuirk extends Quirk {

    public static final ResourceLocation ID = new ResourceLocation(BnHA.MODID, "zoom");

    private float zoom = 1;

    public ZoomQuirk() {
        super(ID);
    }

    @Override public void onUse(EntityPlayerMP player) {}

    @Override public void onUse(EntityPlayerSP player) {}

    @Override public void load(NBTTagCompound in) {
        zoom = (float) Math.max(in.getFloat("zoom"), 0.01);
    }

    @Override public NBTTagCompound save() {
        NBTTagCompound out = new NBTTagCompound();
        out.setFloat("zoom", zoom);
        return out;
    }


    @SideOnly(Side.CLIENT)
    @SubscribeEvent public void onFOVUpdate(FOVUpdateEvent event) {
        event.setNewfov(zoom);
    }

    @SubscribeEvent public void tick(TickEvent.ClientTickEvent event) {
        if(KeyBindings.zoomIn.isPressed() && zoom > 0.01) {
            zoom -= .01;
            Helper.syncQuirkWithServer(this);
        } else if(KeyBindings.zoomOut.isPressed() && zoom < 1) {
            zoom += .01;
            Helper.syncQuirkWithServer(this);
        }
    }
}
