package net.cleannrooster.aureate.entities;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class SeethingLightning extends LightningEntity {
    private int ambientTick;
    private int remainingActions;
    public SeethingLightning(EntityType<? extends LightningEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.ambientTick = 2;
        this.seed = this.random.nextLong();
        this.remainingActions = this.random.nextInt(3) + 1;
    }

    @Override
    public void tick() {
        baseTick();
        if (this.ambientTick == 2) {
            if (this.world.isClient()) {
                this.world.playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.PLAYERS, 2.0F, 0.5F + this.random.nextFloat() * 0.2F, false);
            }
        }
        --this.ambientTick;
        Iterator var2;
        List list;
        if (this.ambientTick < 0) {
            if (this.remainingActions == 0) {


                this.discard();

            }
            else if (this.ambientTick < -this.random.nextInt(10)) {
                --this.remainingActions;
                this.ambientTick = 1;
                this.seed = this.random.nextLong();
            }

        }
    }
}
