package net.cleannrooster.aureate.items;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;

import static net.minecraft.entity.effect.StatusEffectCategory.BENEFICIAL;

public class Flurrying extends StatusEffect {
    public Flurrying() {
        super(
                BENEFICIAL, // whether beneficial or harmful for entities
                0x98D982); // color in RGB
    }
    @Override
    public double adjustModifierAmount(int amplifier, EntityAttributeModifier modifier) {
        return modifier.getValue();
    }
}
