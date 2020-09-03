package me.maki325.bokunoheroacademia.quirks;

import com.google.common.base.Predicate;
import me.maki325.bokunoheroacademia.BnHA;
import me.maki325.bokunoheroacademia.Helper;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.IQuirk;
import me.maki325.bokunoheroacademia.api.capabilities.quirk.QuirkProvider;
import me.maki325.bokunoheroacademia.api.quirk.Quirk;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class EraserQuirk extends Quirk {

    public static final ResourceLocation ID = new ResourceLocation(BnHA.MODID, "ereaser");

    public double distance = 20;

    public EraserQuirk() {
        super(ID);
    }

    @Override public void onUse(EntityPlayerMP player) {
        Vec3d position = player.getPositionVector();
        RayTraceResult result = getRayTraceBlock(player);
        if(result == null) return;
        RayTraceResult rayTraceResult = getRayTraceEntity(player);
        if(rayTraceResult == null || rayTraceResult.hitVec.distanceTo(position) > distance) return;
        if(result.typeOfHit == RayTraceResult.Type.BLOCK && rayTraceResult.hitVec.distanceTo(position) > result.hitVec.distanceTo(position) || rayTraceResult.typeOfHit != RayTraceResult.Type.ENTITY) return;
        if(rayTraceResult.entityHit == null || !(rayTraceResult.entityHit instanceof EntityPlayer)) return;

        EntityPlayerMP target = (EntityPlayerMP) rayTraceResult.entityHit;

        IQuirk iquirk = target.getCapability(QuirkProvider.QUIRK_CAP, null);
        if(iquirk == null) return;
        if(iquirk.getQuirks() == null || iquirk.getQuirks().get(0) == null) return;
        Quirk quirk = iquirk.getQuirks().get(0);
        quirk.setErased(!quirk.isErased());

        Helper.syncQuirkWithClient(quirk, target, true);
    }

    @SideOnly(Side.CLIENT)
    @Override public void onUse(EntityPlayerSP player) {}

    public RayTraceResult getRayTraceEntity(EntityPlayerMP player) {
        Vec3d vector3d = player.getPositionEyes(0.0F);

        double d = distance;

        Vec3d vector3d1 = player.getLook(1.0F);
        Vec3d vector3d2 = vector3d.add(new Vec3d(vector3d1.x * d, vector3d1.y * d, vector3d1.z * d));

        AxisAlignedBB axisalignedbb = player.getEntityBoundingBox().expand(vector3d1.scale(d).x, vector3d1.scale(d).y, vector3d1.scale(d).z).grow(1.0D, 1.0D, 1.0D);
        RayTraceResult entityraytraceresult = rayTraceEntities(player, vector3d, vector3d2, axisalignedbb, (p_215312_0_) -> {
            return p_215312_0_ instanceof EntityPlayer && !p_215312_0_.noClip && p_215312_0_.canBeCollidedWith();
        }, d * d);

        return entityraytraceresult;
    }

    public RayTraceResult rayTraceEntities(EntityPlayerMP shooter, Vec3d startVec, Vec3d endVec, AxisAlignedBB boundingBox, Predicate<Entity> filter, double distance) {
        World world = shooter.world;
        double d0 = distance;
        Entity entity = null;
        Vec3d vector3d = null;

        for(Entity entity1 : world.getEntitiesInAABBexcluding(shooter, boundingBox, filter)) {
            AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow((double)entity1.getCollisionBorderSize());
            Optional<Vec3d> optional = rayTrace(axisalignedbb, startVec, endVec);
            if (axisalignedbb.contains(startVec)) {
                if (d0 >= 0.0D) {
                    entity = entity1;
                    vector3d = optional.orElse(startVec);
                    d0 = 0.0D;
                }
            } else if (optional.isPresent()) {
                Vec3d vector3d1 = optional.get();
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

        return entity == null ? null : new RayTraceResult(entity, vector3d);
    }

    public Optional<Vec3d> rayTrace(AxisAlignedBB aabb,Vec3d p_216365_1_, Vec3d p_216365_2_) {
        double[] adouble = new double[]{1.0D};
        double d0 = p_216365_2_.x - p_216365_1_.x;
        double d1 = p_216365_2_.y - p_216365_1_.y;
        double d2 = p_216365_2_.z - p_216365_1_.z;
        EnumFacing direction = func_197741_a(aabb, p_216365_1_, adouble, null, d0, d1, d2);
        if (direction == null) {
            return Optional.empty();
        } else {
            double d3 = adouble[0];
            return Optional.of(p_216365_1_.add(new Vec3d(d3 * d0, d3 * d1, d3 * d2)));
        }
    }

    @Nullable
    private static EnumFacing func_197741_a(AxisAlignedBB aabb, Vec3d p_197741_1_, double[] p_197741_2_, @Nullable EnumFacing facing, double p_197741_4_, double p_197741_6_, double p_197741_8_) {
        if (p_197741_4_ > 1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_4_, p_197741_6_, p_197741_8_, aabb.minX, aabb.minY, aabb.maxY, aabb.minZ, aabb.maxZ, EnumFacing.WEST, p_197741_1_.x, p_197741_1_.y, p_197741_1_.z);
        } else if (p_197741_4_ < -1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_4_, p_197741_6_, p_197741_8_, aabb.maxX, aabb.minY, aabb.maxY, aabb.minZ, aabb.maxZ, EnumFacing.EAST, p_197741_1_.x, p_197741_1_.y, p_197741_1_.z);
        }

        if (p_197741_6_ > 1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_6_, p_197741_8_, p_197741_4_, aabb.minY, aabb.minZ, aabb.maxZ, aabb.minX, aabb.maxX, EnumFacing.DOWN, p_197741_1_.y, p_197741_1_.z, p_197741_1_.x);
        } else if (p_197741_6_ < -1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_6_, p_197741_8_, p_197741_4_, aabb.maxY, aabb.minZ, aabb.maxZ, aabb.minX, aabb.maxX, EnumFacing.UP, p_197741_1_.y, p_197741_1_.z, p_197741_1_.x);
        }

        if (p_197741_8_ > 1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_8_, p_197741_4_, p_197741_6_, aabb.minZ, aabb.minX, aabb.maxX, aabb.minY, aabb.maxY, EnumFacing.NORTH, p_197741_1_.z, p_197741_1_.x, p_197741_1_.y);
        } else if (p_197741_8_ < -1.0E-7D) {
            facing = func_197740_a(p_197741_2_, facing, p_197741_8_, p_197741_4_, p_197741_6_, aabb.maxZ, aabb.minX, aabb.maxX, aabb.minY, aabb.maxY, EnumFacing.SOUTH, p_197741_1_.z, p_197741_1_.x, p_197741_1_.y);
        }

        return facing;
    }

    @Nullable
    private static EnumFacing func_197740_a(double[] p_197740_0_, @Nullable EnumFacing p_197740_1_, double p_197740_2_, double p_197740_4_, double p_197740_6_, double p_197740_8_, double p_197740_10_, double p_197740_12_, double p_197740_14_, double p_197740_16_, EnumFacing p_197740_18_, double p_197740_19_, double p_197740_21_, double p_197740_23_) {
        double d0 = (p_197740_8_ - p_197740_19_) / p_197740_2_;
        double d1 = p_197740_21_ + d0 * p_197740_4_;
        double d2 = p_197740_23_ + d0 * p_197740_6_;
        if (0.0D < d0 && d0 < p_197740_0_[0] && p_197740_10_ - 1.0E-7D < d1 && d1 < p_197740_12_ + 1.0E-7D && p_197740_14_ - 1.0E-7D < d2 && d2 < p_197740_16_ + 1.0E-7D) {
            p_197740_0_[0] = d0;
            return p_197740_18_;
        } else {
            return p_197740_1_;
        }
    }

    public RayTraceResult getRayTraceBlock(EntityPlayerMP player) {
        double blockReachDistance = distance;
        float partialTicks = 0.0F;
        Vec3d vec3d = player.getPositionEyes(partialTicks);
        Vec3d vec3d1 = player.getLook(partialTicks);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
        return player.world.rayTraceBlocks(vec3d, vec3d2, false, false, true);
    }

}
