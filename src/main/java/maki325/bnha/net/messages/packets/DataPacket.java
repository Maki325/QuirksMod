package maki325.bnha.net.messages.packets;

import maki325.bnha.api.functional.Function;
import net.minecraft.nbt.NBTTagCompound;

public class DataPacket {
	
	public NBTTagCompound prepareMessageData;
	public Function processMessageData;
	
	public DataPacket(NBTTagCompound prepareMessageData, Function processMessageData) {
		this.prepareMessageData = prepareMessageData;
		this.processMessageData = processMessageData;
	}
	
}
