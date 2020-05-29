package com.crimzonmodz.bokunoheroacademia.quirks;

import com.crimzonmodz.bokunoheroacademia.BnHA;
import com.crimzonmodz.bokunoheroacademia.api.quirk.Quirk;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

public class TestQuirk extends Quirk {

    public TestQuirk() { super(new ResourceLocation(BnHA.MODID, "test_quirk"), "Test quirk"); }

    @Override public void onPlayerUse(ServerPlayerEntity player, ServerWorld world) { player.sendMessage(new StringTextComponent("Quirk used")); }

    @Override public void onPlayerJoin(ServerPlayerEntity player) { player.sendMessage(new StringTextComponent("You joined!!!")); }

}
