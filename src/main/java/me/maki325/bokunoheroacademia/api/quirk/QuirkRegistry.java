package me.maki325.bokunoheroacademia.api.quirk;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class QuirkRegistry {

    public static final Map<ResourceLocation, Supplier<Quirk>> QUIRKS = new HashMap<>();

    public static void addQuirk(ResourceLocation id, Supplier<Quirk> quirk) {
        QUIRKS.put(id, quirk);
    }

    public static Quirk get(ResourceLocation id) {
        return QUIRKS.get(id).get();
    }

    public static Quirk get(String id) {
        return QUIRKS.get(new ResourceLocation(id)).get();
    }

    public static Quirk get(NBTTagCompound in) {
        ResourceLocation id = new ResourceLocation(in.getString("id"));
        Quirk quirk = QUIRKS.get(id).get();
        if(quirk == null) return null;
        quirk.load(in);
        return quirk;
    }

}
