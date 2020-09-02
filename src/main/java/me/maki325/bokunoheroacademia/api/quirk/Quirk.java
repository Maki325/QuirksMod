package me.maki325.bokunoheroacademia.api.quirk;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public abstract class Quirk {

    public final ResourceLocation id;

    public Quirk(ResourceLocation id) {
        this.id = id;
    }

    public ResourceLocation getId() {
        return id;
    }

    public abstract void onUse(EntityPlayerMP player);
    public abstract void onUse(EntityPlayerSP player);

    public NBTTagCompound save() { return new NBTTagCompound(); }
    public void load(NBTTagCompound in) {}

}
