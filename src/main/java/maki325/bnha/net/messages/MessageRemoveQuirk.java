package maki325.bnha.net.messages;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import maki325.bnha.api.Quirk;
import maki325.bnha.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import scala.actors.threadpool.Arrays;

public class MessageRemoveQuirk implements IMessage {

	private boolean isMessageValid;
	
	private String playerName;
	private int length;
	private List<Quirk> quirks;
	
	public MessageRemoveQuirk() {
		isMessageValid = false;
	}
	
	public MessageRemoveQuirk(String playerName, Quirk... quirks) {
		this.playerName = playerName;
		this.quirks = new ArrayList<Quirk>(Arrays.asList(quirks));
		this.length = quirks.length;
		isMessageValid = true;
	}
	
	public int getLength() {
		return length;
	}
	
	public List<Quirk> getQuirks() {
		return quirks;
	}
	
	public String getPlayerName() {
		return playerName;
	}

	public boolean isMessageValid() {
		return isMessageValid;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		playerName = ByteBufUtils.readUTF8String(buf);
		length = buf.readInt();
		quirks = new ArrayList<Quirk>();
		for(int i = 0;i < length;i++) {
			quirks.add(Utils.nbtToQuirk(ByteBufUtils.readTag(buf)));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, playerName);
		buf.writeInt(length);
		for(Quirk q:quirks) {
			ByteBufUtils.writeTag(buf, q.toNBT());
		}
	}

}
