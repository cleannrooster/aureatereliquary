package net.cleannrooster.aureate.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.sound.SoundEvents;

import static net.minecraft.entity.effect.StatusEffectCategory.BENEFICIAL;
import static net.minecraft.entity.effect.StatusEffectCategory.HARMFUL;

public class AureateBlast extends StatusEffect {
        public AureateBlast() {
            super(
                    HARMFUL, // whether beneficial or harmful for entities
                    0x98D982); // color in RGB
        }

        @Override
        public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
            super.onRemoved(entity, attributes, amplifier);
        }


}
