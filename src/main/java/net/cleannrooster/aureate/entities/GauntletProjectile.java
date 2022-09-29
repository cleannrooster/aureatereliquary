package net.cleannrooster.aureate.entities;

import net.cleannrooster.aureate.StatusEffectsModded;
import net.cleannrooster.aureate.aureate;
import net.cleannrooster.aureate.items.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.*;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static net.cleannrooster.aureate.entities.MirrorEntity.OWNER;

public class GauntletProjectile extends ThrownItemEntity{

    boolean squall;
    LivingEntity target2;

    public GauntletProjectile(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }
    public GauntletProjectile(World world, LivingEntity owner, boolean squall1, @Nullable LivingEntity target) {
        super(aureate.GAUNTLET_PROJECTILE_ENTITY_TYPE, owner, world); // null will be changed later
        this.squall = squall1;
        this.target2 = target;
    }
    public GauntletProjectile(World world, double x, double y, double z) {
        super(aureate.GAUNTLET_PROJECTILE_ENTITY_TYPE, x, y, z, world); // null will be changed later
    }

    @Override
    protected Item getDefaultItem() {
        return Items.AIR; // We will configure this later, once we have created the ProjectileItem.
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() { // Not entirely sure, but probably has do to with the snowball's particles. (OPTIONAL)
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.FLAME : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) { // Also not entirely sure, but probably also has to do with the particles. This method (as well as the previous one) are optional, so if you don't understand, don't include this one.
        if (status == 3) {
            ParticleEffect particleEffect = new DustParticleEffect(new Vec3f(Vec3d.unpackRgb(	16766720)),1F);

            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    @Override
    public void tick() {

        if(this.getOwner() == null){
            this.discard();
        }
        if (this.age > 80) {
            this.discard();
        }
        if(this.squall && this.target2 != null){
            this.setNoGravity(true);
            this.noClip = false;
            super.tick();

            this.setNoGravity(true);
            this.noClip = false;
            Vec3d vec3 = this.target2.getBoundingBox().getCenter().subtract(this.getPos());
            this.setPos(this.getX(), this.getY() + vec3.y * 0.015D * (double)2, this.getZ());
            /*if (this.world.isClient()) {
                this.getY() = this.getY();
            }*/

            double d0 = 0.05D * (double)2;
            this.setVelocity(this.getVelocity().multiply(0.95D).add(vec3.normalize().multiply(d0)));
            int num_pts = 200;
            if(this.age % 5 == 1) {
                for (int i = 0; i <= num_pts; i = i + 1) {
                    double[] indices = IntStream.rangeClosed(0, (int) ((1000 - 0) / 1))
                            .mapToDouble(x -> x * 1 + 0).toArray();

                    double phi = Math.acos(1 - 2 * indices[i] / num_pts);
                    double theta = Math.PI * (1 + Math.pow(5, 0.5) * indices[i]);
                    double x = cos(theta) * sin(phi);
                    double y = Math.sin(theta) * sin(phi);
                    double z = cos(phi);
                    PacketByteBuf buf = PacketByteBufs.create();

                    buf.writeDouble(4 * x + this.getX());
                    buf.writeDouble(4 * y + this.getY());
                    buf.writeDouble(4 * z + this.getZ());

                    if (!this.world.isClient()) {
                        for (PlayerEntity player : this.world.getPlayers()) {
                            ServerPlayNetworking.send((ServerPlayerEntity) player, Objects.requireNonNull(Identifier.tryParse("a")), buf);
                        }
                    }
                }
                List<Entity> others = this.world.getOtherEntities(this, this.getBoundingBox().expand(4));
                for (Entity target : others) {
                    if (target instanceof LivingEntity living && target != this.getOwner()) {

                        living.damage(DamageSource.FREEZE, 1F);
                    }
                }
            }
            super.tick();
        }
        else {
            float tridentEntity = this.getYaw() % 360;

            float f = this.getPitch();
            float g = -MathHelper.sin(tridentEntity * ((float) Math.PI / 180)) * MathHelper.cos(f * ((float) Math.PI / 180));
            float h = -MathHelper.sin(f * ((float) Math.PI / 180));
            float k2 = MathHelper.cos(tridentEntity * ((float) Math.PI / 180)) * MathHelper.cos(f * ((float) Math.PI / 180));
            float l2 = MathHelper.sqrt(g * g + h * h + k2 * k2);
            float m2 = 2.0f;
            float f7 = 360 + this.getYaw() % 360;

            int ii = this.age % 20;
            final double theta = Math.toRadians((ii / 10d) * 360d);
            double x = Math.cos(theta);
            double y = Math.sin(theta);
            double z = 0;
            Vector3d vec3d = rotate(x, y, z, -Math.toRadians(f7), Math.toRadians(f), 0);
            /*final double angle = Math.toRadians(((double) ii / 20) * 360d);
                double x = Math.cos(angle);
                double y = Math.sin(angle);
                double z = 0;*/
            PacketByteBuf buf = PacketByteBufs.create();
            double x1 = this.getX() + 0.5 * vec3d.x;
            double y1 = this.getY() + 0.5 * vec3d.y;
            double z1 = this.getZ() + 0.5 * vec3d.z;
            double xx1 = this.getX() - 0.5 * vec3d.x;
            double yy1 = this.getY() - 0.5 * vec3d.y;
            double zz1 = this.getZ() - 0.5 * vec3d.z;
            this.world.addImportantParticle(new DustParticleEffect(new Vec3f(Vec3d.unpackRgb(16766720)), 1.0F), true, x1, y1, z1, 0, 0, 0);
            this.world.addImportantParticle(new DustParticleEffect(new Vec3f(Vec3d.unpackRgb(16766720)), 1.0F), true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);

            this.world.addImportantParticle(new DustParticleEffect(new Vec3f(Vec3d.unpackRgb(16766720)), 1.0F), true, xx1, yy1, zz1, 0, 0, 0);
            this.setNoGravity(true);

            super.tick();
        }
    }
    public Vector3d rotate(double x, double y, double z, double pitch, double roll, double yaw) {
        double cosa = Math.cos(yaw);
        double sina = Math.sin(yaw);

        double cosb = Math.cos(pitch);
        double sinb = Math.sin(pitch);
        double cosc = Math.cos(roll);
        double sinc = Math.sin(roll);

        double Axx = cosa * cosb;
        double Axy = cosa * sinb * sinc - sina * cosc;
        double Axz = cosa * sinb * cosc + sina * sinc;

        double Ayx = sina * cosb;
        double Ayy = sina * sinb * sinc + cosa * cosc;
        double Ayz = sina * sinb * cosc - cosa * sinc;

        double Azx = -sinb;
        double Azy = cosb * sinc;
        double Azz = cosb * cosc;

        Vector3d vec3 = new Vector3d(0,0,0);
        vec3.x = Axx * x + Axy * y + Axz * z;
        vec3.y = Ayx * x + Ayy * y + Ayz * z;
        vec3.z = Azx * x + Azy * y + Azz * z;
        return vec3;
    }

    @Override
    protected void onBlockCollision(BlockState state) {
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if(this.squall){
            return;
        }
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, (byte)3);
            this.discard();
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if(this.squall){
            return;
        }
        if(this.getOwner() instanceof PlayerEntity player && entityHitResult.getEntity() instanceof LivingEntity living) {

            MirrorEntity mirror = new MirrorEntity(aureate.MIRROR_ENTITY, player.getWorld());
            NbtCompound nbt = new NbtCompound();
            nbt.putString("mirror", player.getUuidAsString());
            mirror.setCustomName(Text.of(player.getGameProfile().getName()));
            mirror.getDataTracker().set(OWNER, Optional.of(player.getUuid()));
            mirror.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 80, 0, false, false));

            mirror.clearGoalsAndTasks();
            mirror.setNoGravity(true);
            mirror.setAiDisabled(true);
            mirror.setSilent(true);
            mirror.setCustomNameVisible(false);
            mirror.isFireImmune();
            mirror.setInvulnerable(true);
            mirror.setHeadYaw(player.getYaw());
            mirror.setPitch(player.getPitch());
            Vec3d vec3 = living.getBoundingBox().getCenter().subtract(player.getPos());
            Vec3d vec32 = vec3.normalize();
            mirror.setPos(living.getX() - living.getBoundingBox().getXLength() * vec32.getX() - vec32.getX(), living.getBlockY(), living.getZ() - living.getBoundingBox().getZLength() * vec32.getZ() - vec32.getZ());
            mirror.equipStack(EquipmentSlot.MAINHAND, player.getMainHandStack());

            mirror.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.MIRROR, 7, 1, false, false));
            mirror.setInvulnerable(true);
            living.getWorld().spawnEntity(mirror);
            mirror.swingHand(Hand.MAIN_HAND);
            player.attack(living);
        }
    }
}
