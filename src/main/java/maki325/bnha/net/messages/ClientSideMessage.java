package maki325.bnha.net.messages;

import io.netty.buffer.ByteBuf;
import maki325.bnha.api.BnHAUtils;
import maki325.bnha.net.messages.packets.DataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientSideMessage extends BasicSidedMessage {

	public ClientSideMessage() {
		this.name = "";
		this.dataPacket = new DataPacket(new NBTTagCompound(), (data, world, pos, player) -> {});
		this.pos = BlockPos.ORIGIN;
	}
	
	public ClientSideMessage(String name, DataPacket dataPacket, BlockPos pos) {
		this.name = name;
		this.dataPacket = dataPacket;
		this.pos = pos;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound tag = ByteBufUtils.readTag(buf);
		this.name = tag.getString("name");
		this.pos = NBTUtil.getPosFromTag(tag.getCompoundTag("position"));
		this.dataPacket = BnHAUtils.deserializeDataPacket(tag.getCompoundTag("dataPacket"));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("name", this.name);
		tag.setTag("position", NBTUtil.createPosTag(this.pos));
		tag.setTag("dataPacket", BnHAUtils.serializeDataPacket(this.dataPacket));
		ByteBufUtils.writeTag(buf, tag);
	}

}
