package maki325.bnha.net.messages;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageClientStuff implements IMessage {

	private String quirkName;
	private boolean messageIsValid;
	private double x, y, z;
	
	public MessageClientStuff() {
		messageIsValid = false;
	}
	
	public MessageClientStuff(String quirkName, double x, double y , double z) {
		this.quirkName = quirkName;
		this.x = x;
		this.y = y;
		this.z = z;
		messageIsValid = true;
	}

	public String getQuirkName() {
		return quirkName;
	}
	
	public boolean isMessageValid() {
		return messageIsValid;
	}

	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		quirkName = ByteBufUtils.readUTF8String(buf);
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		messageIsValid = true;
	}

	@Override
	public void toBytes(ByteBuf buf) {

		if (!messageIsValid) return;
		
		ByteBufUtils.writeUTF8String(buf, quirkName);
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
	}

}
