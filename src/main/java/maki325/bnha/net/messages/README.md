# Messages Package

### MessageFactory.java

#### MessageFactory Class

Creates a **SimpleNetworkWrapper** with id of the mod. Also initializes all messages. Has functions for sending messages: `sendDataToClient` and `sendDataToServer`.



#### BasicSidedMessage class

Implementation of `IMessage` and used for creating messages.

#### BasicSidedMessageHandler class

An abstract class implementing `IMessageHandler` and used for creating message handlers.



#### ResponsiveSidedMessage class

Implementation of `IMessage` and used for creating messages that will return a second message on receiving the first one.

#### ResponsiveSidedMessageHandler class

An abstract class implementing `IMessageHandler` and used for creating message handlers that will send another message after receiving the first one.