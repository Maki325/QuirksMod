package me.maki325.bokunoheroacademia.api.capabilities.quirk;

import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public interface IQuirk {

    List<Quirk> getQuirks();

    void addQuirks(Quirk... quirk);

    void removeQuirks(Quirk... quirk);

    Quirk getQuirk(int index);

    Quirk getQuirk(ResourceLocation id);

    void clear();

}