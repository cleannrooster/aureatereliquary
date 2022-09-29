package net.cleannrooster.aureate.items;

import net.cleannrooster.aureate.StatusEffectsModded;
import net.cleannrooster.aureate.entities.GauntletProjectile;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;

import java.util.UUID;

public class FlurryBlade extends SwordItem {
    public FlurryBlade(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(entity instanceof PlayerEntity player){
            if(player.getMainHandStack().getItem() == this || player.getOffHandStack().getItem() == this){
                player.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.FLURRYING.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, "9fad3c6e-3061-11ed-a261-0242ac120002",0.5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL),5,0, false, false));
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player) {
            if (!player.getItemCooldownManager().isCoolingDown(this)) {

                int amplifier = 0;
                if(!FabricLoader.getInstance().isModLoaded("bettercombat") && !(player.getOffHandStack().getItem() instanceof Gauntlet)) {
                    player.getItemCooldownManager().set(this, (int) (20F / (float) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED)));
                }                if (attacker.hasStatusEffect(StatusEffectsModded.ETHEREAL)) {
                    attacker.setAbsorptionAmount(attacker.getAbsorptionAmount() + 1);
                }

                if(!attacker.hasStatusEffect(StatusEffectsModded.FANOFKNIVES)) {
                    attacker.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.FANOFKNIVES, (int) (1 * (1 + 20F / (attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED)))), (int) (1 * (1 + 20F / attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED))), false, false));
                }

            }
        }
        return super.postHit(stack, target, attacker);
    }
}
