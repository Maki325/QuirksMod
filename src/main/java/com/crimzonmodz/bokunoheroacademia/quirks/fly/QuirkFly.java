package com.crimzonmodz.bokunoheroacademia.quirks.fly;

import com.crimzonmodz.bokunoheroacademia.BnHA;
import com.crimzonmodz.bokunoheroacademia.Config;
import com.crimzonmodz.bokunoheroacademia.Helper;
import com.crimzonmodz.bokunoheroacademia.api.quirk.LevelableQuirk;
import com.crimzonmodz.bokunoheroacademia.api.quirk.ModelQuirk;
import com.crimzonmodz.bokunoheroacademia.api.quirk.Tickable;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class QuirkFly extends LevelableQuirk implements Tickable, ModelQuirk {

	//public static final double startMaxCooldown = 20 * 120 /* 20 ticks * 60sec */, startMaxActiveTime = 20 * 10 /* 20 ticks * 10sec */;

	@OnlyIn(Dist.CLIENT)
	private List<LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>> layers;

	public static final ResourceLocation ID = new ResourceLocation(BnHA.MODID, "fly");

	private boolean isActive = false, isAvailable = true;
	private double cooldown = 0, maxCooldown = Config.QUIRK_FLY_INIT_MAX_COOLDOWN.get();
	private double activeTime = 0, maxActiveTime = Config.QUIRK_FLY_INIT_MAX_ACTIVE_TIME.get();

	ServerPlayerEntity player = null;
	
	public QuirkFly() {
		super(ID);
		nextXp = Config.QUIRK_FLY_FIRST_LEVEL_XP.get();//200
		xpPerUse = Config.QUIRK_FLY_XP_PER_TICK.get();//1
	}

	@Override
	public void onPlayerUse(ServerPlayerEntity player, ServerWorld world) {
		if(!isAvailable) return;
		if(!isActive) {
			player.abilities.allowFlying = true;
			isActive = true;

			player.sendMessage(new StringTextComponent(TextFormatting.GREEN + "Quirk Activated"));

			player.sendPlayerAbilities();
			sync();
		} else {
			if(!player.isCreative()) {
				player.abilities.allowFlying = false;
				player.abilities.isFlying = false;
			}
			isActive = false;

			player.sendMessage(new StringTextComponent(TextFormatting.RED + "Quirk Deactivated"));
			player.sendPlayerAbilities();
			sync();
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override public void onClientUse(ClientPlayerEntity player, ClientWorld world) {
		super.onClientUse(player, world);
		player.world.addParticle(ParticleTypes.DRAGON_BREATH, true, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 1, 1, 1);
	}

	@Override public void onLevelUp() {
		player.sendMessage(new StringTextComponent("You leveled up: " + level));
		maxCooldown *= Config.QUIRK_FLY_COOLDOWN_MULTIPLIER.get();//0.9;
		maxActiveTime *= Config.QUIRK_FLY_ACTIVE_TIME_MULTIPLIER.get();//1.15;
	}

	@Override
	public void onPlayerJoin(ServerPlayerEntity player) {
		super.onPlayerJoin(player);
		this.player = player;

		player.sendMessage(new StringTextComponent("Your quirk is flight. Current level: " + level));
	}

	@Override
	public CompoundNBT save() {
		CompoundNBT tag = super.save();
		tag.putBoolean("isActive", this.isActive);
		tag.putBoolean("isAvailable", this.isAvailable);
		tag.putDouble("maxCooldown", this.maxCooldown);
		tag.putDouble("maxActiveTime", this.maxActiveTime);

		tag.putDouble("cooldown", this.cooldown);
		tag.putDouble("activeTime", this.activeTime);
		
		return tag;
	}
	
	@Override
	public void load(CompoundNBT tag) {
		super.load(tag);

		this.isActive = tag.getBoolean("isActive");
		this.isAvailable = tag.getBoolean("isAvailable");

		this.maxCooldown = tag.getDouble("maxCooldown");
		if(this.maxCooldown == 0) this.maxCooldown = Config.QUIRK_FLY_INIT_MAX_COOLDOWN.get();
		this.cooldown = tag.getDouble("cooldown");

		this.maxActiveTime = tag.getDouble("maxActiveTime");
		if(this.maxActiveTime == 0) this.maxActiveTime = Config.QUIRK_FLY_INIT_MAX_ACTIVE_TIME.get();;
		this.activeTime = tag.getDouble("activeTime");
	}
	
	@Override
	@SubscribeEvent
	public void tick(TickEvent.ServerTickEvent event) {
		if(!isAvailable) {
			cooldown++;
			if(cooldown >= maxCooldown) {
				isAvailable = true;
				cooldown = 0;
			}
		}
		
		if(isActive) {
			updateXP();

			activeTime++;
			if(activeTime >= maxActiveTime) {
				isActive = false;
				isAvailable = false;
				activeTime = 0;
				
				if(this.player != null) {
					if(!this.player.isCreative()) {
						player.abilities.allowFlying = false;
						player.abilities.isFlying = false;
						this.player.sendPlayerAbilities();
					}

					sync();
					this.player.sendMessage(new StringTextComponent(TextFormatting.RED + "Quirk Deactivated"));
				}
			}
		}
	}

	public void sync() {
		Helper.syncQuirkWithClient(this, player);
		/*System.out.println("!!!Sync!!!");
		CompoundNBT nbt = new CompoundNBT();
		System.out.println("player.getUniqueID(): " + player.getUniqueID().toString());
		nbt.putUniqueId("playerId", player.getUniqueID());
		nbt.putBoolean("enable", enable);
		Helper.sendToAll(player, new PacketFunction((world, g, data) -> {
			try {
				System.out.println("data.getUniqueId(\"playerId\"): " + data.getUniqueId("playerId").toString());
				PlayerEntity playerEntity = world.getPlayerByUuid(data.getUniqueId("playerId"));
				System.out.println("playerEntity: " + playerEntity);
				if(playerEntity == null) return;
				LazyOptional<IQuirk> lo = playerEntity.getCapability(QuirkProvider.QUIRK_CAP);
				IQuirk iq = lo.orElse(null);
				System.out.println("iq: " + iq);
				if(iq == null || iq.getQuirk(QuirkFly.ID) == null) return;
				( (QuirkFly) iq.getQuirk(QuirkFly.ID) ).isActive = data.getBoolean("enable");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, nbt), false);
		Networking.INSTANCE.sendTo(new PacketFunction((world, playerEntity, data) -> {
			try {
				System.out.println("client playerEntity: " + playerEntity);
				if(playerEntity == null) return;
				LazyOptional<IQuirk> lo = playerEntity.getCapability(QuirkProvider.QUIRK_CAP);
				IQuirk iq = lo.orElse(null);
				System.out.println("client iq: " + iq);
				if(iq == null || iq.getQuirk(QuirkFly.ID) == null) return;
				( (QuirkFly) iq.getQuirk(QuirkFly.ID) ).isActive = data.getBoolean("enable");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, nbt), player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);*/
	}

	@Override
	public String toString() {
		return "QuirkFly{" +
				"isActive=" + isActive +
				", isAvailable=" + isAvailable +
				", cooldown=" + cooldown +
				", maxCooldown=" + maxCooldown +
				", activeTime=" + activeTime +
				", maxActiveTime=" + maxActiveTime +
				", player=" + player +
				", level=" + level +
				", xp=" + xp +
				", nextXp=" + nextXp +
				", xpPerUse=" + xpPerUse +
				'}';
	}

	public boolean isActive() {
		return isActive;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public List<LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>> getLayers(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> renderer) {
		if(layers == null) initLayers(renderer);
		return layers;
	}

	@OnlyIn(Dist.CLIENT)
	public void initLayers(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> renderer) {
		layers = new ArrayList<>();
		layers.add(new WingsLayer(renderer));
	}

}
