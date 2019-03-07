package maki325.bnha.net.messages;

import maki325.bnha.Reference;
import maki325.bnha.api.functional.Function;
import maki325.bnha.net.messages.packets.DataPacket;
import maki325.bnha.net.messages.packets.ResponsiveDataPacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class MessageFactory {
	
	private static SimpleNetworkWrapper stream;
	private static int id = 0;

	public static void initClient() {
        stream.registerMessage(ClientSideHandler.class, ClientSideMessage.class, ++id, Side.CLIENT);
        stream.registerMessage(ResponsiveClientMessageHandler.class, ResponsiveClientMessage.class, ++id, Side.CLIENT);
	}
	
    public static void initCommon() {
    	stream = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
        
        stream.registerMessage(ServerSideHandler.class, ServerSideMessage.class, ++id, Side.SERVER);
        stream.registerMessage(ResponsiveServerMessageHandler.class, ResponsiveServerMessage.class, ++id, Side.SERVER);
    }
    
    public static void sendDataToClient(String messageName, EntityPlayerMP player, BlockPos pos, NBTTagCompound prepareData, Function processData) {
    	DataPacket dataPacket = new DataPacket(prepareData, processData);
    	ClientSideMessage clientMessage = new ClientSideMessage(messageName, dataPacket, pos);
        stream.sendTo(clientMessage, player);
    }
    
    public static void sendDataToServer(String messageName, BlockPos pos, NBTTagCompound prepareData, Function processData) {
    	DataPacket dataPacket = new DataPacket(prepareData, processData);
    	ServerSideMessage serverMessage = new ServerSideMessage(messageName, dataPacket, pos);
        stream.sendToServer(serverMessage);
    }

    public static void sendDataToServerWithResponse(String messageName, BlockPos pos, NBTTagCompound prepareMessageData, Function processMessageData, NBTTagCompound prepareResponseData, Function processResponseData) {
    	ResponsiveDataPacket responsiveDataPacket = new ResponsiveDataPacket(
                prepareMessageData,
                processMessageData,
                prepareResponseData,
                processResponseData
        );
    	ResponsiveServerMessage responsiveServerMessage = new ResponsiveServerMessage(messageName, responsiveDataPacket, pos);
        stream.sendToServer(responsiveServerMessage);
    }
    
    public static void sendDataToClientWithResponse(String messageName, BlockPos pos, EntityPlayerMP player, NBTTagCompound prepareMessageData, Function processMessageData, NBTTagCompound prepareResponseData, Function processResponseData) {
    	ResponsiveDataPacket responsiveDataPacket = new ResponsiveDataPacket(
                prepareMessageData,
                processMessageData,
                prepareResponseData,
                processResponseData
        );
    	ResponsiveClientMessage responsiveClientMessage = new ResponsiveClientMessage(messageName, responsiveDataPacket, pos);
        stream.sendTo(responsiveClientMessage, player);
    }

}

abstract class BasicSidedMessage implements IMessage {
	String name = "";
	DataPacket dataPacket = null;
	BlockPos pos = BlockPos.ORIGIN;
}

abstract class ResponsiveSidedMessage implements IMessage {
	String name = "";
	ResponsiveDataPacket dataPacket = null;
	BlockPos pos = BlockPos.ORIGIN;
}

abstract class BasicSidedMessageHandler<M extends BasicSidedMessage> implements IMessageHandler<M, IMessage>{}
abstract class ResponsiveSidedMessageHandler<M extends ResponsiveSidedMessage, R extends BasicSidedMessage> implements IMessageHandler<M, R>{}
