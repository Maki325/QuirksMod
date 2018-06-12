package maki325.bnha.net.messages;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageParticle implements IMessage {
	
	private double x,y,z;
	private int particleID;
	private boolean messageIsValid;
	
	public MessageParticle() {
		messageIsValid = false;
	}
	
	public MessageParticle(double x, double y , double z, int particleID) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.particleID = particleID;
		messageIsValid = true;
	}
	
	public int getParticleID() {
		return particleID;
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
	
	public boolean isMessageValid() {
		return messageIsValid;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		particleID = buf.readInt();
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		
		messageIsValid = true;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		if (!messageIsValid) return;
		
		buf.writeInt(particleID);
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
	}

}
