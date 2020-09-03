package me.maki325.bokunoheroacademia.setup;

import me.maki325.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import me.maki325.bokunoheroacademia.network.Networking;
import me.maki325.bokunoheroacademia.network.packates.ActivateQuirkPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientSetup extends CommonProxy {

    @Override
    public void init() {
        MinecraftForge.EVENT_BUS.register(ClientSetup.class);

        KeyBindings.init();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onKeyInput(TickEvent.ClientTickEvent event) {
        if(KeyBindings.activate.isPressed()) {
            IQuirk iquirkClient = Minecraft.getMinecraft().player.getCapability(QuirkProvider.QUIRK_CAP, null);
            if(iquirkClient != null && !iquirkClient.getQuirks().isEmpty()) {
                Quirk quirkClient = iquirkClient.getQuirks().get(0);
                if (quirkClient != null || quirkClient.isErased()) {
                    quirkClient.onUse(Minecraft.getMinecraft().player);
                }
            }

            Networking.INSTANCE.sendToServer(new ActivateQuirkPacket());
        }
    }

}
