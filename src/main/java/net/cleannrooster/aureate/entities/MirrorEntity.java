package net.cleannrooster.aureate.entities;

import com.mojang.authlib.GameProfile;
import net.cleannrooster.aureate.StatusEffectsModded;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HuskEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class MirrorEntity extends HuskEntity {
    public PlayerEntity mirrorOf;
    public PlayerEntity player;
    public static TrackedData<Optional<UUID>> OWNER;
    public boolean horizontalCollision = false;
    public boolean verticalCollision = false;


static{
    OWNER = DataTracker.registerData(MirrorEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
}

    public MirrorEntity(EntityType<? extends HuskEntity> entityType, World world) {
        super(entityType, world);
        setNoGravity(true);
        this.horizontalCollision = false;
        this.verticalCollision = false;
        this.noClip = true;
    }


    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(OWNER, Optional.empty());
    }

    @Override
    public boolean isAttacking() {
        return super.isAttacking();
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
    }

    @Override
    public void tick() {
        if(!this.hasStatusEffect(StatusEffectsModded.MIRROR) && !this.getWorld().isClient()){
            this.discard();
        }
        this.noClip = true;
        super.tick();
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public boolean collidesWith(Entity other) {
        return false;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    @Override
    protected boolean shouldDropLoot() {
        return false;
    }

    @Override
    public boolean shouldDropXp() {
        return false;
    }
}
