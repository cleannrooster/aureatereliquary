package net.cleannrooster.aureate.items;

import net.cleannrooster.aureate.StatusEffectsModded;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class AureateMace extends AxeItem {
    public AureateMace(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player) {
            int added = 0;
                if (attacker.hasStatusEffect(StatusEffects.STRENGTH)) {
                    int amplifier = attacker.getStatusEffect(StatusEffects.STRENGTH).getAmplifier()+1;
                    added = (int) (player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) - amplifier * 3);
                }
                else {
                    added = (int) (player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
                }
                if (added < 3) {
                    added = 3;
                }
                if (!player.getItemCooldownManager().isCoolingDown(this)) {
                    if(!FabricLoader.getInstance().isModLoaded("bettercombat") && !(player.getOffHandStack().getItem() instanceof Gauntlet)) {
                        player.getItemCooldownManager().set(this, (int) (20F / (float) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED)));
                    }
                    target.hurtTime = 0;
                    target.timeUntilRegen = 0;
                    target.damage(DamageSource.MAGIC, (float) (0.1F * player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)));

                    if (attacker.hasStatusEffect(StatusEffectsModded.ETHEREAL)) {
                        attacker.setAbsorptionAmount(attacker.getAbsorptionAmount() + 2);
                    }
                    if (attacker.hasStatusEffect(StatusEffects.STRENGTH)) {
                        int amplifier = attacker.getStatusEffect(StatusEffects.STRENGTH).getAmplifier();
                        if (((amplifier + 1) * 3 + 3) > added) {
                            attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, (int) (80F / (float) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED)), (int) (added / 3) - 1, false, false));

                        } else {
                            attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, (int) (80F / (float) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED)), amplifier + 1, false, false));
                        }

                    } else {
                        attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, (int) (80F / (float) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED)), 0, false, false));
                    }


                }
            }
        return super.postHit(stack, target, attacker);
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return ActionResult.FAIL;
    }
}
