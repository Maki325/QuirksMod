package maki325.bnha.net.messages.packets;

import maki325.bnha.api.functional.Function;
import net.minecraft.nbt.NBTTagCompound;

public class ResponsiveDataPacket {
	
	public NBTTagCompound prepareMessageData; public Function processMessageData;
	public NBTTagCompound prepareResponseData; public Function processResponseData;
	
	public ResponsiveDataPacket(NBTTagCompound prepareMessageData, Function processMessageData, NBTTagCompound prepareResponseData, Function processResponseData) {
		this.prepareMessageData = prepareMessageData; this.processMessageData = processMessageData;
		this.prepareResponseData = prepareResponseData; this.processResponseData = processResponseData;
	}
	
}