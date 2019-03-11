# Quirk Package



### Quirk.java

A serializable abstract class for quirks.

Adds`public abstract void onPlayerUse(EntityPlayerMP player, WorldServer world)` function that is called when the player uses the quirk.



### LevelableQuirk.java

Extension of `Quirk.java` that adds ability to level up the quirk.



### TickableQuirk.java

Extension of `Quirk.java` that adds a function that is called every tick from the server side.