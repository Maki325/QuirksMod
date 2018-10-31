package maki325.bnha.net.quirk.hud;

import io.netty.buffer.ByteBuf;
import maki325.bnha.net.points.MessageChangePoints.Change;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageChangeHudSkill implements IMessage {

	private String playeName;
	private String skillName;
	private int position;
	private boolean isValid;
	private Change change;
	
	public MessageChangeHudSkill() {
		isValid = false;
	}
	
	public MessageChangeHudSkill(String skillName, int position, Change change, String playeName) {
		isValid = true;
		this.playeName = playeName;
		this.skillName = skillName;
		this.position = position;
		this.change = change;
	}
	
	public boolean isValid() {
		return isValid;
	}
	
	public String getSkillName() {
		return skillName;
	}
	
	public int getPosition() {
		return position;
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
		this.skillName = ByteBufUtils.readUTF8String(buf);
		this.change = Change.values()[buf.readInt()];
		this.position = buf.readInt();
		this.isValid = true;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, playeName);
		ByteBufUtils.writeUTF8String(buf, skillName);
		buf.writeInt(position);
		buf.writeInt(change.value);
	}
	
	public enum Change {
		SET(0), GET(1);
		
		int value;
		Change(int value) {
			this.value = value;
		}
	}

}
