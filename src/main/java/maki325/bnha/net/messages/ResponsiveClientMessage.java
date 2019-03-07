package maki325.bnha.net.messages;

import io.netty.buffer.ByteBuf;
import maki325.bnha.api.BnHAUtils;
import maki325.bnha.net.messages.packets.ResponsiveDataPacket;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class ResponsiveClientMessage extends ResponsiveSidedMessage {

	public ResponsiveClientMessage() {
		this.name = "";
		this.dataPacket = new ResponsiveDataPacket(new NBTTagCompound(), (data, world, pos, player) -> {}, new NBTTagCompound(), (data, world, pos, player) -> {});
		this.pos = BlockPos.ORIGIN;
	}
	
	public ResponsiveClientMessage(String name, ResponsiveDataPacket dataPacket, BlockPos pos) {
		this.name = name;
		this.dataPacket = dataPacket;
		this.pos = pos;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound tag = ByteBufUtils.readTag(buf);
		this.name = tag.getString("name");
		this.pos = NBTUtil.getPosFromTag(tag.getCompoundTag("position"));
		this.dataPacket = BnHAUtils.deserializeResponsiveDataPacket(tag.getCompoundTag("dataPacket"));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("name", this.name);
		tag.setTag("position", NBTUtil.createPosTag(this.pos));
		tag.setTag("dataPacket", BnHAUtils.serializeResponsiveDataPacket(this.dataPacket));
		ByteBufUtils.writeTag(buf, tag);
	}

}
