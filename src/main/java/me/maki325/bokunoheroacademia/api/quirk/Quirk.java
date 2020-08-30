package me.maki325.bokunoheroacademia.api.quirk;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

public abstract class Quirk {

    public final ResourceLocation id;

    public Quirk(ResourceLocation id) {
        this.id = id;
    }

    public ResourceLocation getId() {
        return id;
    }

    public abstract void onUse(ServerPlayerEntity player);
    public abstract void onUse(ClientPlayerEntity player);

    public CompoundNBT save() { return new CompoundNBT(); }
    public void load(CompoundNBT in) {}

}
