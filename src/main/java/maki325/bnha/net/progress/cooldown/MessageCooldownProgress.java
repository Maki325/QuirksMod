package maki325.bnha.net.progress.cooldown;

import io.netty.buffer.ByteBuf;
import maki325.bnha.api.net.BnHAMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageCooldownProgress extends BnHAMessage {

	private int cooldownProgress;
	
	public MessageCooldownProgress() {
		super();
	}
	
	public MessageCooldownProgress(int cooldownProgress) {
		super(null);
		this.cooldownProgress = cooldownProgress;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		cooldownProgress = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(cooldownProgress);
	}

	public int getCooldownProgress() {
		return cooldownProgress;
	}
	
}
