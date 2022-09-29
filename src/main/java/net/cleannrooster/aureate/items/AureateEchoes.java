package net.cleannrooster.aureate.items;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

import static net.minecraft.entity.effect.StatusEffectCategory.BENEFICIAL;

public class AureateEchoes extends StatusEffect {

    public AureateEchoes() {
        super(
                BENEFICIAL, // whether beneficial or harmful for entities
                0x98D982); // color in RGB
    }
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.hurtTime = 0;
        entity.timeUntilRegen = 0;
        entity.damage(DamageSource.LIGHTNING_BOLT,amplifier/10F);
        entity.hurtTime = 0;
        entity.timeUntilRegen = 0;
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {

        super.onRemoved(entity, attributes, amplifier);

    }
}
