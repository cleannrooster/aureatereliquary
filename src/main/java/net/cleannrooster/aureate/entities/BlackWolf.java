package net.cleannrooster.aureate.entities;

import net.cleannrooster.aureate.StatusEffectsModded;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.world.World;

public class BlackWolf extends WolfEntity {
    public BlackWolf(EntityType<? extends WolfEntity> entityType, World world) {
        super(entityType, world);
    }
    @Override
    public void tick() {
        super.tick();
        if(!this.hasStatusEffect(StatusEffectsModded.ETHEREALWOLF)){
            this.discard();
        }
    }
    public static DefaultAttributeContainer.Builder setAttributes() {
        return WolfEntity.createWolfAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 2*0.30000001192092896D);
    }

    @Override
    protected void pushAway(Entity entity) {

    }

    @Override
    public void pushAwayFrom(Entity entity) {

    }

    @Override
    public void takeKnockback(double strength, double x, double z) {
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
