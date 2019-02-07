package maki325.bnha.net.progress.active;

import io.netty.buffer.ByteBuf;
import maki325.bnha.api.net.BnHAMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageActiveProgress extends BnHAMessage {

	private int activeProgress;
	
	public MessageActiveProgress() {
		super();
	}
	
	public MessageActiveProgress(int activeProgress) {
		super(null);
		this.activeProgress = activeProgress;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		activeProgress = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(activeProgress);
	}
	
	public int getActiveProgress() {
		return activeProgress;
	}
	
}
