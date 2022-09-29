package net.cleannrooster.aureate.entities;

import net.cleannrooster.aureate.StatusEffectsModded;
import net.cleannrooster.aureate.items.ModItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

import static net.minecraft.entity.effect.StatusEffectCategory.NEUTRAL;

public class EtherealWolf extends StatusEffect {
    public EtherealWolf() {
        super(
                NEUTRAL, // whether beneficial or harmful for entities
                0x98D982); // color in RGB
    }
}
