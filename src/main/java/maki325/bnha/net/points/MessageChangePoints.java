package maki325.bnha.net.points;

import io.netty.buffer.ByteBuf;
import maki325.bnha.api.Quirk;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageChangePoints implements IMessage {

	private String playeName;
	private int points;
	private boolean isValid;
	private Change change;
	
	public MessageChangePoints() {
		isValid = false;
	}
	
	public MessageChangePoints(int points, Change change, String playeName) {
		isValid = true;
		this.playeName = playeName;
		this.points = points;
		this.change = change;
	}
	
	public boolean isValid() {
		return isValid;
	}
	
	public int getPoints() {
		return points;
	}
	
	public String getPlayeName() {
		return playeName;
	}
	
	public Change getChange() {
		return change;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.playeName = ByteBufUtils.readUTF8String(buf);
		this.points = buf.readInt();
		this.change = Change.values()[buf.readInt()];
		this.isValid = true;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, playeName);
		buf.writeInt(points);
		buf.writeInt(change.value);
	}
	
	public enum Change {
		ADD(0), REMOVE(1), SET(2), RESET(3), GET(4);
		
		int value;
		Change(int value) {
			this.value = value;
		}
	}

}
