package com.crimzonmodz.bokunoheroacademia.api.quirk;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.Serializable;

public abstract class Quirk implements Serializable {

    private final ResourceLocation id;
    private String name;

    public Quirk(ResourceLocation id) {
        this(id, id.getPath());
    }

    public Quirk(ResourceLocation id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Function called every time player uses a quirk.
     * @param player - player whose quirk should be activated
     */
    public abstract void onPlayerUse(ServerPlayerEntity player, ServerWorld world);

    @OnlyIn(Dist.CLIENT)
    public void onClientUse(ClientPlayerEntity player, ClientWorld world) {}

    // Static functions

    public static boolean isSimilar(Quirk q1, Quirk q2) { return q1.getId().equals(q2.getId()); }


    // Overridable functions

    public CompoundNBT save() { return new CompoundNBT(); };
    public void load(CompoundNBT tag) {};

    public void onPlayerJoin(ServerPlayerEntity player) {}
    public void onPlayerLeave(ServerPlayerEntity player) {}
    public void onPlayerDeath(ServerPlayerEntity player) {}


    // Other functions

    public boolean isSimilar(Quirk q) { return id.equals(q.getId()); }


    // Getters and setters

    public ResourceLocation getId() { return id; }

    public String getName() { return name; }

    @Override public String toString() { return "Quirk - ID: " + id + ", Name: " + name; }

}
