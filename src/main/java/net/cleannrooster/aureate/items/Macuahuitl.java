package net.cleannrooster.aureate.items;

import net.cleannrooster.aureate.StatusEffectsModded;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Macuahuitl extends SwordItem {
    public Macuahuitl(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if(entity instanceof PlayerEntity player){
            if(player.getMainHandStack().getItem() instanceof Macuahuitl || player.getOffHandStack().getItem() instanceof Macuahuitl){
                int count = 0;
                    //player.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.BLOODTHIRST.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,"076e5b38-30a5-11ed-a261-0242ac120002",-7F/8F, EntityAttributeModifier.Operation.MULTIPLY_TOTAL),30,0,false,false));
                for( LivingEntity entity2 : world.getEntitiesByClass(LivingEntity.class,player.getBoundingBox().expand(4), EntityPredicates.VALID_LIVING_ENTITY)){
                    if(entity2.hasStatusEffect(StatusEffectsModded.MAIMED)){
                        count = count+1;
                    }
                }
                if(count>0) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 30, count - 1, false, false));
                }
            }
        }
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player) {
            if (!player.getItemCooldownManager().isCoolingDown(this)) {
                int modifier = 1;
                if(player.hasStatusEffect(StatusEffectsModded.BLOODTHIRST)){
                    modifier = 8;
                }
                if(!FabricLoader.getInstance().isModLoaded("bettercombat") && !(player.getOffHandStack().getItem() instanceof Gauntlet)) {
                    player.getItemCooldownManager().set(this, (int) (20F / (float) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED)));
                }                if (attacker.hasStatusEffect(StatusEffectsModded.ETHEREAL)) {
                    attacker.setAbsorptionAmount(attacker.getAbsorptionAmount() + 2);
                }
                if(target.hasStatusEffect(StatusEffectsModded.MAIMED)) {
                    if (target.getStatusEffect(StatusEffectsModded.MAIMED).getAmplifier() <= modifier*7*attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)) {
                        target.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.MAIMED, 400, target.getStatusEffect(StatusEffectsModded.MAIMED).getAmplifier() +  (int) Math.ceil(attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE))*modifier, false, false));
                    }
                    else{
                        target.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.MAIMED, 400, (int) Math.ceil(8*attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)*modifier), false, false));

                    }
                }
                else{
                    target.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.MAIMED, 400, (int) Math.ceil(attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)*modifier), false, false));
                }


            }
        }
        return super.postHit(stack, target, attacker);
    }

}
