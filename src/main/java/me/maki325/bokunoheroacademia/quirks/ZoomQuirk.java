package me.maki325.bokunoheroacademia.quirks;

import me.maki325.bokunoheroacademia.BnHA;
import me.maki325.bokunoheroacademia.Helper;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import me.maki325.bokunoheroacademia.setup.KeyBindings;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ZoomQuirk extends Quirk {

    public static final ResourceLocation ID = new ResourceLocation(BnHA.MODID, "zoom");

    private float zoom = 1;

    public ZoomQuirk() {
        super(ID);
    }

    @Override public void onUse(ServerPlayerEntity player) {}

    @Override public void onUse(ClientPlayerEntity player) {}

    @Override public void load(CompoundNBT in) {
        zoom = in.getFloat("zoom");
    }

    @Override public CompoundNBT save() {
        CompoundNBT out = new CompoundNBT();
        out.putFloat("zoom", zoom);
        return out;
    }

    @OnlyIn(Dist.CLIENT)
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
