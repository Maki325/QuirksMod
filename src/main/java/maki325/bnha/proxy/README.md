# Proxy Package



### CommonProxy.java

Code called on both server and client side.

#### PreInit:

- MessageFactory - common init
- Registering GuiHandler
  - GuiHandlerST - just for testing functions. Later will be used for skilltree rendering



#### Init:

- Registering CapabilityHandler



# ClientProxy.java

Code called on client side only.

#### PreInit:

- everything in `CommonProxy.preInit()`
- MessageFactory - client init



#### Init:

- everything in `CommonProxy.init()`

- Registering CapabilityHandler

