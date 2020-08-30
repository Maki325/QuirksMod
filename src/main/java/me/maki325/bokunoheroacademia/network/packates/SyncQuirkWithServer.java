package me.maki325.bokunoheroacademia.network.packates;

import me.maki325.bokunoheroacademia.Helper;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import me.maki325.bokunoheroacademia.api.quirk.QuirkRegistry;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncQuirkWithServer {

    private CompoundNBT data;

    public SyncQuirkWithServer(PacketBuffer buf) {
        data = buf.readCompoundTag();
    }

    public SyncQuirkWithServer(CompoundNBT data) {
        this.data = data;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeCompoundTag(data);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();

            LazyOptional<IQuirk> lo = player.getCapability(QuirkProvider.QUIRK_CAP);
            IQuirk iq = lo.orElse(null);
            if(iq == null) return;

            Quirk q = iq.getQuirk(new ResourceLocation(data.getString("quirkName")));
            if(q == null) {
                q = QuirkRegistry.get(data.getString("quirkName"));
                if(q == null) {
                    player.sendMessage(new StringTextComponent("No quirk with name " + data.getString("quirkName")), player.getUniqueID());
                    return;
                }
            }
            q.load(data.getCompound("quirkData"));
            // TODO: Do I need this?
            // iq.addQuirks(q);

            Helper.syncQuirkWithClient(q, player, true);

            player.sendMessage(new StringTextComponent("SUCCESSFULL"), player.getUniqueID());
        });
        ctx.get().setPacketHandled(true);
    }

}
