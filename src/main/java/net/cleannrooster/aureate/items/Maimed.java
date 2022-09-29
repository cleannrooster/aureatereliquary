package net.cleannrooster.aureate.items;

import net.cleannrooster.aureate.StatusEffectsModded;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

import static net.minecraft.entity.effect.StatusEffectCategory.HARMFUL;

public class Maimed extends StatusEffect {
    public Maimed() {
        super(
                HARMFUL, // whether beneficial or harmful for entities
                0x98D982); // color in RGB
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
            return duration % 10 == 1;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        int i = 0;
        int duration = entity.getStatusEffect(StatusEffectsModded.MAIMED).getDuration();
        if(entity.isAlive()) {
            float amount = (float) (0.5 * (amplifier)/8);
            amount = (float) Math.min(amount,entity.getMaxHealth()*0.1);
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 0, false, false));
            if (entity.getHealth() + entity.getAbsorptionAmount() > entity.getMaxHealth()*0.10 + amount && entity.isAlive()) {

                entity.hurtTime = 0;
                entity.timeUntilRegen = 0;
                entity.damage(DamageSource.GENERIC, amount);
                entity.hurtTime = 0;
                entity.timeUntilRegen = 0;

            }
            else if(entity.getHealth() + entity.getAbsorptionAmount() <= entity.getMaxHealth()*0.10 + amount && entity.isAlive() && entity.getHealth() + entity.getAbsorptionAmount() > entity.getMaxHealth()*0.10 ){
                entity.hurtTime = 0;
                entity.timeUntilRegen = 0;
                entity.damage(DamageSource.GENERIC, (float) (entity.getHealth()-entity.getMaxHealth()*0.10));
                entity.hurtTime = 0;
                entity.timeUntilRegen = 0;
            }
            else {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 2, false, false));
            }
            if(amplifier > 0) {
                entity.removeStatusEffect(StatusEffectsModded.MAIMED);
                entity.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.MAIMED, duration - 1, (int) (amplifier - Math.ceil((float) amplifier / 8)), false, false));
            }
            else{
                entity.removeStatusEffect(StatusEffectsModded.MAIMED);

            }
        }
    }
}
