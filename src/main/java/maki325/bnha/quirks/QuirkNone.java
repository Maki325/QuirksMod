package maki325.bnha.quirks;

import maki325.bnha.api.Quirk;
import net.minecraft.entity.player.EntityPlayer;

public class QuirkNone extends Quirk {

	public QuirkNone() {
		super("none");
		
		init();
	}

	@Override
	public void onPlayerUse(EntityPlayer player) {
		
	}

}
