package net.cleannrooster.aureate.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.sound.SoundEvents;

import static net.minecraft.entity.effect.StatusEffectCategory.HARMFUL;

public class Quantum extends StatusEffect {

    public Quantum() {
        super(
                HARMFUL, // whether beneficial or harmful for entities
                0x98D982); // color in RGB
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {

        super.onApplied(entity, attributes, amplifier);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);
    }
}
