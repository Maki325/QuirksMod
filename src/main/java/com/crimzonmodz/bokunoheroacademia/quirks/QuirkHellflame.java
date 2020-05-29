package com.crimzonmodz.bokunoheroacademia.quirks;

import com.crimzonmodz.bokunoheroacademia.BnHA;
import com.crimzonmodz.bokunoheroacademia.api.quirk.Quirk;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Explosion;
import net.minecraft.world.server.ServerWorld;

public class QuirkHellflame extends Quirk {

	public QuirkHellflame() { super(new ResourceLocation(BnHA.MODID, "hellflame"), "Hellflame"); }

	@Override
	public void onPlayerUse(ServerPlayerEntity player, ServerWorld world) {
		world.createExplosion(player, DamageSource.causePlayerDamage(player), player.getPosX(), player.getPosY(), player.getPosZ(), 0, true, Explosion.Mode.NONE);
	}

}
