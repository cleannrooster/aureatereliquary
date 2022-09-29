package net.cleannrooster.aureate.entities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;

import static net.minecraft.entity.effect.StatusEffectCategory.NEUTRAL;

public class MirrorZombie extends StatusEffect {
    public MirrorZombie() {
        super(
                NEUTRAL, // whether beneficial or harmful for entities
                0x98D982); // color in RGB
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.setInvulnerable(true);
        if (amplifier > 0) {
            entity.setInvisible(true);
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        entity.setInvulnerable(true);
        if (amplifier > 0) {
            entity.setInvisible(true);
        }
        super.onApplied(entity, attributes, amplifier);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);
        entity.discard();
    }
}
