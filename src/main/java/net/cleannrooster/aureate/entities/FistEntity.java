package net.cleannrooster.aureate.entities;

import net.cleannrooster.aureate.aureate;
import net.cleannrooster.aureate.items.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FistEntity extends TridentEntity {
    public FistEntity(EntityType<? extends TridentEntity> entityType, World world) {
        super(entityType, world);
    }
    public FistEntity(World world, LivingEntity owner, double x,double y,double z) {
        super(aureate.FIST, world);
        this.setPosition(x, y, z);
        this.setOwner(owner);
    }



    @Override
    public void tick() {
        if (this.getOwner() != null) {
            if(this.distanceTo(this.getOwner())> 5) {
                this.remove(RemovalReason.DISCARDED);
            }
        }

        super.tick();
    }


    @Override
    protected ItemStack asItemStack() {
        return null;
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        return false;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        this.playSound(this.getSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.discard();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if(entityHitResult.getEntity() instanceof LivingEntity living && this.getOwner() instanceof PlayerEntity player){
                    living.hurtTime = 0;
                    living.timeUntilRegen = 0;
                        player.attack(living);
                    living.hurtTime = 0;
                    living.timeUntilRegen = 0;
                    SoundEvent soundEvent = SoundEvents.ITEM_TRIDENT_HIT;
                    this.playSound(soundEvent, 1.0F, 1.0F);

        }
    }

}
