package net.cleannrooster.aureate.entities;

import net.cleannrooster.aureate.StatusEffectsModded;
import net.cleannrooster.aureate.aureate;
import net.cleannrooster.aureate.items.Firebrand;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FirebrandProjectile extends ThrownItemEntity {
    public LivingEntity target;
    public boolean overload = false;
    public float amount = 0;
    public boolean bool;
    public boolean bool2;
    public List<LivingEntity> list = new ArrayList<>();
    public FirebrandProjectile(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public FirebrandProjectile(World world, LivingEntity owner) {
        super(aureate.FIREBRAND_PROJECTILE_ENTITY_TYPE, owner, world); // null will be changed later
    }

    public FirebrandProjectile(World world, double x, double y, double z) {
        super(aureate.FIREBRAND_PROJECTILE_ENTITY_TYPE, x, y, z, world); // null will be changed later
    }
    @Override
    protected Item getDefaultItem() {
        return Items.BLAZE_POWDER; // We will configure this later, once we have created the ProjectileItem.
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() { // Not entirely sure, but probably has do to with the snowball's particles. (OPTIONAL)
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.FLAME : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) { // Also not entirely sure, but probably also has to do with the particles. This method (as well as the previous one) are optional, so if you don't understand, don't include this one.
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
    }

    @Override
    protected void onBlockCollision(BlockState state) {

    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
    }

    @Override
    protected void onCollision(HitResult hitResult) {

    }

    @Override
    public void tick() {
        if(this.age > 200){
           this.discard();
        }
        if(firstUpdate){
            if(this.world.getEntitiesByType(TypeFilter.instanceOf(FirebrandProjectile.class), this.getBoundingBox().expand(16), EntityPredicates.VALID_ENTITY).toArray().length > 64){
                this.discard();
            }
        }
        if(this.target != null){
            this.setOnFireFor(2);
            if(this.bool && this.target.isOnFire()){
                this.discard();
            }
            if(bool2 && !this.target.isOnFire()){
                this.discard();
            }
            this.setNoGravity(true);
            this.noClip = false;
            super.tick();

            this.setNoGravity(true);
            this.noClip = false;
            Vec3d vec3 = target.getBoundingBox().getCenter().subtract(this.getPos());
            this.setPos(this.getX(), this.getY() + vec3.y * 0.015D * (double)2, this.getZ());
            /*if (this.world.isClient()) {
                this.getY() = this.getY();
            }*/

            double d0 = 0.05D * (double)2;
            this.setVelocity(this.getVelocity().multiply(0.95D).add(vec3.normalize().multiply(d0)));
            if(this.getBoundingBox().intersects(target.getBoundingBox())){
                if(this.getOwner() instanceof PlayerEntity && target.isOnFire()) {
                    Firebrand.FluxFlux(this.target, (PlayerEntity) this.getOwner(),this.list);
                    this.discard();
                }
                target.setOnFireFor(4);
                this.discard();
            }
        }
        else{
            super.tick();
        }
    }
}
