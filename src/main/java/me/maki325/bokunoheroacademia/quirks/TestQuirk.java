package me.maki325.bokunoheroacademia.quirks;

import me.maki325.bokunoheroacademia.BnHA;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TestQuirk extends Quirk {

    public static final ResourceLocation ID = new ResourceLocation(BnHA.MODID, "test_quirk");

    public TestQuirk() {
        super(ID);
    }

    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        event.player.sendMessage(new TextComponentString("Test Quirk"));
    }

    @Override
    public void onUse(EntityPlayerMP player) {
        player.sendMessage(new TextComponentString("Server Side!!!"));
    }

    @SideOnly(Side.CLIENT)
    @Override public void onUse(EntityPlayerSP player) {
        player.sendChatMessage("Client Side!!!");
    }

    @Override
    public NBTTagCompound save() {
        return new NBTTagCompound();
    }

    @Override
    public void load(NBTTagCompound in) {}

}
