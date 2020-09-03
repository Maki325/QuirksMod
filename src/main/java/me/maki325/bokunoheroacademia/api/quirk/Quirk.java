package me.maki325.bokunoheroacademia.api.quirk;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class Quirk {

    public final ResourceLocation id;

    private boolean erased = false;

    public Quirk(ResourceLocation id) {
        this.id = id;
    }

    public ResourceLocation getId() {
        return id;
    }

    public abstract void onUse(EntityPlayerMP player);
    @SideOnly(Side.CLIENT)
    public abstract void onUse(EntityPlayerSP player);

    public NBTTagCompound save() { return new NBTTagCompound(); }
    public void load(NBTTagCompound in) {}

    public boolean isErased() {
        return erased;
    }

    public void setErased(boolean erased) {
        this.erased = erased;
    }
}
