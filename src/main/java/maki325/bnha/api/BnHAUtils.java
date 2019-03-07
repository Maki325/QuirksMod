package maki325.bnha.api;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import maki325.bnha.api.functional.Function;
import maki325.bnha.net.messages.packets.DataPacket;
import maki325.bnha.net.messages.packets.ResponsiveDataPacket;
import net.minecraft.nbt.NBTTagCompound;

public class BnHAUtils {

	//For Function
	public static Function deserializeFunction(NBTTagCompound tag) {
		try {
			ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(tag.getByteArray("function")));
			Function fnc = (Function) iStream.readObject();
			iStream.close();
			return fnc;
		} catch (IOException | ClassNotFoundException e) {
			return (data, world, pos, player) -> { System.err.println("FUNCTION COULDN'T BE DESERIALIZED!!!"); };
		}
	}
	
	public static NBTTagCompound serializeFunction(Function fnc) {
		NBTTagCompound tag = new NBTTagCompound();
		try {
			ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			ObjectOutput oo = new ObjectOutputStream(bStream); 
			oo.writeObject(fnc);
			oo.close();
			tag.setByteArray("function", bStream.toByteArray());
			return tag;
		} catch (IOException e) {
			System.err.println("FUNCTION COULDN'T BE SERIALIZED!!!");
			return tag;
		}
	}
	
	//For DataPacket
	public static DataPacket deserializeDataPacket(NBTTagCompound tag) {
		return new DataPacket(tag.getCompoundTag("prepareMessageData"), deserializeFunction(tag.getCompoundTag("processMessageData")));
	}
	
	public static NBTTagCompound serializeDataPacket(DataPacket dp) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("prepareMessageData", dp.prepareMessageData);
		tag.setTag("processMessageData", serializeFunction(dp.processMessageData));
		return tag;
	}
	
	//For ResponsiveDataPacket
	public static ResponsiveDataPacket deserializeResponsiveDataPacket(NBTTagCompound tag) {
		return new ResponsiveDataPacket(tag.getCompoundTag("prepareMessageData"), deserializeFunction(tag.getCompoundTag("processMessageData")),
										tag.getCompoundTag("prepareResponseData"), deserializeFunction(tag.getCompoundTag("processResponseData")));
	}
	
	public static NBTTagCompound serializeResponsiveDataPacket(ResponsiveDataPacket dp) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("prepareMessageData", dp.prepareMessageData);
		tag.setTag("processMessageData", serializeFunction(dp.processMessageData));
		tag.setTag("prepareResponseData", dp.prepareResponseData);
		tag.setTag("processResponseData", serializeFunction(dp.processResponseData));
		return tag;
	}
	
}
