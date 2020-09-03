package me.maki325.bokunoheroacademia.quirks;

import me.maki325.bokunoheroacademia.BnHA;
import me.maki325.bokunoheroacademia.Helper;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Optional;
import java.util.function.Predicate;

public class EraserQuirk extends Quirk {

    public static final ResourceLocation ID = new ResourceLocation(BnHA.MODID, "ereaser");

    public int distance = 20;

    public EraserQuirk() {
        super(ID);
    }

    @Override public void onUse(ServerPlayerEntity player) {
        Vector3d position = player.getPositionVec();
        RayTraceResult result = getRayTraceBlock(player);
        if(result == null) return;
        EntityRayTraceResult rayTraceResult = getRayTraceEntity(player);
        if(rayTraceResult == null) return;
        if(result.getType() == RayTraceResult.Type.BLOCK && rayTraceResult.getHitVec().distanceTo(position) > result.getHitVec().distanceTo(position) || rayTraceResult.getType() != RayTraceResult.Type.ENTITY) return;
        if(rayTraceResult.getEntity() == null || !(rayTraceResult.getEntity() instanceof PlayerEntity)) return;

        ServerPlayerEntity target = (ServerPlayerEntity) rayTraceResult.getEntity();

        LazyOptional<IQuirk> lazyOptional = target.getCapability(QuirkProvider.QUIRK_CAP);
        IQuirk iquirk = lazyOptional.orElse(null);
        if(iquirk == null) return;
        if(iquirk.getQuirks() == null || iquirk.getQuirks().get(0) == null) return;
        Quirk quirk = iquirk.getQuirks().get(0);
        quirk.setErased(!quirk.isErased());

        Helper.syncQuirkWithClient(quirk, target, true);
    }

    @OnlyIn(Dist.CLIENT)
    @Override public void onUse(ClientPlayerEntity player) {}

    public EntityRayTraceResult getRayTraceEntity(ServerPlayerEntity player) {
        Vector3d vector3d = player.getEyePosition(0.0F);

        int d = distance;

        Vector3d vector3d1 = player.getLook(1.0F);
        Vector3d vector3d2 = vector3d.add(vector3d1.x * d, vector3d1.y * d, vector3d1.z * d);

        AxisAlignedBB axisalignedbb = player.getBoundingBox().expand(vector3d1.scale(d)).grow(1.0D, 1.0D, 1.0D);
        EntityRayTraceResult entityraytraceresult = rayTraceEntities(player, vector3d, vector3d2, axisalignedbb, (p_215312_0_) -> {
            return !p_215312_0_.isSpectator() && p_215312_0_.canBeCollidedWith();
        }, d * d);

        return entityraytraceresult;
    }

    public static EntityRayTraceResult rayTraceEntities(ServerPlayerEntity shooter, Vector3d startVec, Vector3d endVec, AxisAlignedBB boundingBox, Predicate<Entity> filter, double distance) {
        World world = shooter.world;
        double d0 = distance;
        Entity entity = null;
        Vector3d vector3d = null;

        for(Entity entity1 : world.getEntitiesInAABBexcluding(shooter, boundingBox, filter)) {
            AxisAlignedBB axisalignedbb = entity1.getBoundingBox().grow((double)entity1.getCollisionBorderSize());
            Optional<Vector3d> optional = axisalignedbb.rayTrace(startVec, endVec);
            if (axisalignedbb.contains(startVec)) {
                if (d0 >= 0.0D) {
                    entity = entity1;
                    vector3d = optional.orElse(startVec);
                    d0 = 0.0D;
                }
            } else if (optional.isPresent()) {
                Vector3d vector3d1 = optional.get();
                double d1 = startVec.squareDistanceTo(vector3d1);
                if (d1 < d0 || d0 == 0.0D) {
                    if (entity1.getLowestRidingEntity() == shooter.getLowestRidingEntity() && !entity1.canRiderInteract()) {
                        if (d0 == 0.0D) {
                            entity = entity1;
                            vector3d = vector3d1;
                        }
                    } else {
                        entity = entity1;
                        vector3d = vector3d1;
                        d0 = d1;
                    }
                }
            }
        }

        return entity == null ? null : new EntityRayTraceResult(entity, vector3d);
    }

    public RayTraceResult getRayTraceBlock(ServerPlayerEntity player) {
        return player.pick(distance, 0.0F, false);
    }

}
