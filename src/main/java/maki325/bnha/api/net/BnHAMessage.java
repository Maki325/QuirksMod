package maki325.bnha.api.net;

import javax.annotation.Nullable;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class BnHAMessage implements IMessage {

	private boolean isValid;
	
	/**
	 * A constructor - set the isValid variable to false
	 */
	public BnHAMessage() {
		isValid = false;
	}
	
	/**
	 * A constructor that validates the message(et the isValid variable to true)
	 * @param objects - anything, its just to validate the message
	 */
	@Nullable
	public BnHAMessage(Object objects) {
		isValid = true;
	}
	
	public boolean isValid() {
		return isValid;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.isValid = true;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
	}

}
