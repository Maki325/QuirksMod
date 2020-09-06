package me.maki325.bokunoheroacademia;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.LanguageMap;

public class LanguageHelper {

    public static final LanguageMap LANGUAGE_MAP = LanguageMap.getInstance();

    public static String get(String id) {
        return LANGUAGE_MAP.func_230503_a_(id);
    }

    public static String get(String id, String defaultValue) {
        return LANGUAGE_MAP.getLanguageData().getOrDefault(id, defaultValue);
    }

    public static String getQuirkName(ResourceLocation id) {
        return getQuirkName(id.toString());
    }

    public static String getQuirkName(String id) {
        String quirkKey = "quirk." + id.replace(':', '.');
        String quirkName = LanguageHelper.get(quirkKey, id);
        //if(!quirkName.equals(id)) quirkName += "(" + id + ")";
        return quirkName;
    }

}
