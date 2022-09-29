package net.cleannrooster.aureate.items;

import net.cleannrooster.aureate.StatusEffectsModded;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

import static net.minecraft.entity.effect.StatusEffectCategory.BENEFICIAL;
import static net.minecraft.entity.effect.StatusEffectCategory.NEUTRAL;

public class EtherealBarrier extends StatusEffect {
    public EtherealBarrier() {
        super(
                NEUTRAL, // whether beneficial or harmful for entities
                0x98D982); // color in RGB
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.

            return true;

    }

    // This method is called when it applies the status effect. We implement custom functionality here.
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.setAbsorptionAmount(entity.getAbsorptionAmount()*0.975F);
        if (entity instanceof PlayerEntity living) {
            float amount = 0;
            if (living.getEquippedStack(EquipmentSlot.CHEST).getItem() == ModItems.AUREATE_CHESTPLATE){
                amount = amount+0.10F;
            }
            if (living.getEquippedStack(EquipmentSlot.FEET).getItem() == ModItems.AUREATE_BOOTS){
                amount = amount+0.10F;
            }
            if (living.getEquippedStack(EquipmentSlot.HEAD).getItem() == ModItems.AUREATE_HELMET){
                amount = amount+0.10F;
            }
            if (living.getEquippedStack(EquipmentSlot.LEGS).getItem() == ModItems.AUREATE_LEGGINGS){
                amount = amount+0.10F;
            }



            if(!living.hasStatusEffect(StatusEffectsModded.ETHEREAL)) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.ETHEREAL, 20 * 20, 0, false, false));
            }
            else{
                living.setAbsorptionAmount(living.getAbsorptionAmount()+amount);
            }
        }
    }
}

