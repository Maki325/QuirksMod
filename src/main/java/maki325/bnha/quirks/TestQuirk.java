package maki325.bnha.quirks;

import maki325.bnha.Reference;
import maki325.bnha.api.FunctionHelper;
import maki325.bnha.api.ResourceIdentifier;
import maki325.bnha.api.quirk.LevelableQuirk;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;

public class TestQuirk extends LevelableQuirk {

	public TestQuirk() {
		super(new ResourceIdentifier(Reference.MOD_ID, "testQuirk"));
	}

	@Override
	public void onPlayerUse(EntityPlayerMP player, WorldServer world) {
		super.onPlayerUse(player, world);
		System.out.println("USE");
		FunctionHelper.createExplosion(world, player, 2, true);
	}

}
