package maki325.bnha.net.quirk.messages;

import io.netty.buffer.ByteBuf;
import maki325.bnha.api.Quirk;

import javax.vecmath.Vector3d;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageActivateClient implements IMessage {

	private boolean isValid;
	private double x, y, z;
	private Quirk quirk;
	
	public MessageActivateClient() {
		isValid = false;
	}
	
	public MessageActivateClient(double x, double y, double z, Quirk quirk) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.quirk = quirk;
		
		isValid = true;
	}
	
	public boolean isValid() {
		return isValid;
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
	
	public Quirk getQuirk() {
		return quirk;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();

		quirk = Quirk.nbtToQuirk(ByteBufUtils.readTag(buf));

		isValid = true;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		
		ByteBufUtils.writeTag(buf, quirk.toNBT());
	}

	
	
}
