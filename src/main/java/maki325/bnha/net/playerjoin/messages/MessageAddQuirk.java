package maki325.bnha.net.playerjoin.messages;

import io.netty.buffer.ByteBuf;
import maki325.bnha.api.Quirk;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageAddQuirk implements IMessage {

	private boolean isValid;
	private Quirk quirk;
	
	public MessageAddQuirk() {
		isValid = false;
	}
	
	public MessageAddQuirk(Quirk quirk) {
		this.quirk = quirk;
		
		isValid = true;
	}
	
	public boolean isValid() {
		return isValid;
	}
	
	public Quirk getQuirk() {
		return quirk;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		quirk = Quirk.nbtToQuirk(ByteBufUtils.readTag(buf));
		isValid = true;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, quirk.toNBT());
	}

}
