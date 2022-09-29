package net.cleannrooster.aureate.items;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.ZombieEntity;

import static net.minecraft.entity.effect.StatusEffectCategory.BENEFICIAL;
import static net.minecraft.entity.effect.StatusEffectCategory.HARMFUL;

public class Seething extends StatusEffect {

    public Seething() {
        super(
                HARMFUL, // whether beneficial or harmful for entities
                0x98D982); // color in RGB
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 2 == 1;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        entity.hurtTime = 0;
        entity.timeUntilRegen = 0;
        entity.damage(DamageSource.LIGHTNING_BOLT,(amplifier/12F));

        entity.hurtTime = 0;
        entity.timeUntilRegen = 0;
    }
}
