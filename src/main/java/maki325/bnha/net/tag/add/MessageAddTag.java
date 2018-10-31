package maki325.bnha.net.tag.add;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageAddTag implements IMessage {

	private boolean isValid;
	private String tag;
	private String player;
	
	public MessageAddTag() {
		this.isValid = false;
	}
	
	public MessageAddTag(String tag, String player) {
		this.isValid = true;
		this.tag = tag;
		this.player = player;
	}
	
	public boolean isValid() {
		return isValid;
	}

	public String getTag() {
		return tag;
	}
	
	public String getPlayer() {
		return player;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.tag = ByteBufUtils.readUTF8String(buf);
		this.player = ByteBufUtils.readUTF8String(buf);
		this.isValid = true;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.tag);
		ByteBufUtils.writeUTF8String(buf, this.player);
	}
	
}
