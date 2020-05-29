package com.crimzonmodz.bokunoheroacademia.api.quirk;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuirkRegistry {

    //public static final ArrayList<Class<? extends Quirk>> QUIRKS = new ArrayList<>();
    public static final HashMap<ResourceLocation, Class<? extends Quirk>> QUIRKS = new HashMap<>();
    private static HashMap<Quirk, UV> UVS = new HashMap<>();

    public static void addQuirk(ResourceLocation id, Class<? extends Quirk> quirk) { QUIRKS.put(id, quirk); }

    public static void addQuirk(HashMap<ResourceLocation, Class<? extends Quirk>> quirks) { QUIRKS.putAll(quirks); }

    public static void removeQuirk(HashMap<ResourceLocation, Class<? extends Quirk>> quirks) { quirks.forEach((resourceLocation, aClass) -> QUIRKS.remove(resourceLocation, aClass)); }

    public static void removeQuirk(List<ResourceLocation> ids) { ids.forEach(id -> QUIRKS.remove(id)); }

    public static int getIndex(Quirk quirk) {
        if(quirk == null) return -1;
        for(int i = 0;i < QUIRKS.size();i++) {
            if(quirk.getClass().equals(QUIRKS.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public static Quirk getQuirk(int index) {
        if(index >= 0 || index < QUIRKS.size()) {
            try {
                return QUIRKS.get(index).newInstance();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static Quirk getQuirk(String id) {
        return getQuirk(new ResourceLocation(id));
    }

    public static Quirk getQuirk(ResourceLocation id) {
        Class<? extends Quirk> c = QUIRKS.get(id);
        if(c == null) return null;
        try {
            return c.newInstance();
        } catch (Exception e) {
            return null;
        }
        /*for(Class<? extends Quirk> q : QUIRKS) {
            if(q != null && q.getId().equals(id)) {
                return q;
            }
        }
        return null;*/
    }

    //public static ArrayList<Quirk> getQuirks() { return QUIRKS; }
    public static Map<ResourceLocation, Class<? extends Quirk>> getQuirks() { return QUIRKS; }

    public static void setUV(Quirk q, int u, int v) { UVS.put(q, new UV(u, v)); }

    public static UV getUV(Quirk q) { return UVS.get(q); }

    public static class UV {
        public int u, v;

        public UV(int u, int v) {
            this.u = u;
            this.v = v;
        }

    }

}
