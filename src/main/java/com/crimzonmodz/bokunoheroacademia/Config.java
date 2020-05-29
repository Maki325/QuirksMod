package com.crimzonmodz.bokunoheroacademia;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Config {

    public static final String CATEGORY_QUIRK = "quirk";
    public static final String QUIRK_FLY = "fly";

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;


    public static ForgeConfigSpec.DoubleValue QUIRK_FLY_INIT_MAX_COOLDOWN;
    public static ForgeConfigSpec.DoubleValue QUIRK_FLY_INIT_MAX_ACTIVE_TIME;
    public static ForgeConfigSpec.DoubleValue QUIRK_FLY_XP_PER_TICK;
    public static ForgeConfigSpec.DoubleValue QUIRK_FLY_FIRST_LEVEL_XP;
    public static ForgeConfigSpec.DoubleValue QUIRK_FLY_COOLDOWN_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue QUIRK_FLY_ACTIVE_TIME_MULTIPLIER;


    static {
        COMMON_BUILDER.comment("Quirk settings").push(CATEGORY_QUIRK);

        setupFlyQuirkConfig();

        COMMON_BUILDER.pop();


        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupFlyQuirkConfig() {
        COMMON_BUILDER.comment("Fly Quirk settings").push(QUIRK_FLY);

        QUIRK_FLY_INIT_MAX_COOLDOWN = COMMON_BUILDER.comment("Initial cooldown value for quirk activation (In ticks)")
                .defineInRange("cooldownTime", 20 * 120 /* 20 ticks * 60sec */, 0, Double.MAX_VALUE);
        QUIRK_FLY_INIT_MAX_ACTIVE_TIME = COMMON_BUILDER.comment("Initial activation time value for quirk (In ticks)")
                .defineInRange("activeTime", 20 * 10 /* 20 ticks * 10sec */, 0, Double.MAX_VALUE);
        QUIRK_FLY_XP_PER_TICK = COMMON_BUILDER.comment("Number of xp per tick that a player is given when the quirk is active")
                .defineInRange("xpPerTick", 1, 0, Double.MAX_VALUE);
        QUIRK_FLY_FIRST_LEVEL_XP = COMMON_BUILDER.comment("Number of xp needed for the first level")
                .defineInRange("firstLevel", 200, 0, Double.MAX_VALUE);
        QUIRK_FLY_COOLDOWN_MULTIPLIER = COMMON_BUILDER.comment("Multiplier for cooldown; Used whenever player levels up")
                .defineInRange("cooldownMultiplier", 0.9, 0, Double.MAX_VALUE);
        QUIRK_FLY_ACTIVE_TIME_MULTIPLIER = COMMON_BUILDER.comment("Multiplier for active time; Used whenever player levels up")
                .defineInRange("activeTimeMultiplier", 1.15, 0, Double.MAX_VALUE);

        COMMON_BUILDER.pop();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {

    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {
    }

}