package me.maki325.bokunoheroacademia.api.capabilities.quirk;

import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CapabilityQuirk implements IQuirk {

    private ArrayList<Quirk> quirks = new ArrayList<Quirk>();

    @Override
    public List<Quirk> getQuirks() {
        return quirks;
    }

    @Override
    public void addQuirks(Quirk... quirk) {
        quirks.addAll(Arrays.asList(quirk));
    }

    @Override
    public void removeQuirks(Quirk... quirk) {
        quirks.removeAll(Arrays.asList(quirk));
    }

    @Override
    public Quirk getQuirk(int index) {
        if(index < 0 || index >= quirks.size()) { return null; }
        return quirks.get(index);
    }

    @Override
    public Quirk getQuirk(ResourceLocation id) {
        for(Quirk q:quirks) {
            if(q.getId().equals(id))
                return q;
        }
        return null;
    }

    @Override
    public void clear() {
        quirks.clear();
    }
}
