package net.cleannrooster.aureate.items;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.village.ZombieSiegeManager;

import static net.minecraft.entity.effect.StatusEffectCategory.BENEFICIAL;

public class Bloodthirst extends StatusEffect {
    public Bloodthirst() {
        super(
                BENEFICIAL, // whether beneficial or harmful for entities
                0x98D982); // color in RGB
    }
}
