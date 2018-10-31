package maki325.bnha.net.quirk.messages;

import io.netty.buffer.ByteBuf;
import maki325.bnha.api.Quirk;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageChangeQuirk implements IMessage {

	private Quirk quirk;
	private boolean isValid;
	private String playeName;
	private boolean quiet;
	
	public MessageChangeQuirk() {
		isValid = false;
	}
	
	public MessageChangeQuirk(Quirk quirk, String playeName, boolean quiet) {
		isValid = true;
		this.quirk = quirk;
		this.playeName = playeName;
		this.quiet = quiet;
	}
	
	public boolean isValid() {
		return isValid;
	}
	
	public Quirk getQuirk() {
		return quirk;
	}
	
	public String getPlayeName() {
		return playeName;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.quirk = Quirk.nbtToQuirk(ByteBufUtils.readTag(buf));
		this.playeName = ByteBufUtils.readUTF8String(buf);
		this.quiet = buf.readBoolean();
		this.isValid = true;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, quirk.toNBT());
		ByteBufUtils.writeUTF8String(buf, playeName);
		buf.writeBoolean(quiet);
	}

	public boolean isQuiet() {
		return quiet;
	}

}
