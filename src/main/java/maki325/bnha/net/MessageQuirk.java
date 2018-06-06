package maki325.bnha.net;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageQuirk implements IMessage {

	private String quirkName;
	private boolean messageIsValid;
	
	public MessageQuirk() {
		messageIsValid = false;
	}
	
	public MessageQuirk(String quirkName) {
		this.quirkName = quirkName;
		messageIsValid = true;
	}
	
	public String getQuirkName() {
		return quirkName;
	}
	
	public boolean isMessageValid() {
		return messageIsValid;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		quirkName = ByteBufUtils.readUTF8String(buf);
		messageIsValid = true;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, quirkName);
		messageIsValid = true;
	}

}
