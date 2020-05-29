package com.crimzonmodz.bokunoheroacademia.network;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

import java.io.Serializable;

@FunctionalInterface
public interface Function extends Serializable {

    public static final long serialVersionUID = 5020870270042079340L;

    void run(World world, PlayerEntity player, CompoundNBT data);

}

