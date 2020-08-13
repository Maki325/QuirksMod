package me.maki325.bokunoheroacademia.quirks;

import me.maki325.bokunoheroacademia.BnHA;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class TestQuirk extends Quirk {

    public static final ResourceLocation ID = new ResourceLocation(BnHA.MODID, "test_quirk");

    public TestQuirk() {
        super(ID);
    }

    @Override
    public void onPlayerJoin(PlayerEntity player) {
        player.sendMessage(new StringTextComponent("onPlayerJoin!!!"), player.getUniqueID());
    }

    @Override
    public void onUse(ServerPlayerEntity player) {
        player.sendMessage(new StringTextComponent("Server Side!!!"), player.getUniqueID());
    }

    @Override
    public void onUse(ClientPlayerEntity player) {
        player.sendChatMessage("Client Side!!!");
    }

    @Override
    public CompoundNBT save() {
        return new CompoundNBT();
    }

    @Override
    public void load(CompoundNBT in) {}

}
