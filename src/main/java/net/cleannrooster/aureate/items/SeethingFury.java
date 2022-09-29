package net.cleannrooster.aureate.items;

import net.cleannrooster.aureate.StatusEffectsModded;
import net.cleannrooster.aureate.aureate;
import net.cleannrooster.aureate.entities.SeethingLightning;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.TypeFilter;

import java.util.List;

public class SeethingFury extends SwordItem {
    public SeethingFury(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player) {
            if (!player.getItemCooldownManager().isCoolingDown(this)) {
                SeethingLightning lightning1 = new SeethingLightning(aureate.SEETHING_LIGHTNING,attacker.getWorld());
                lightning1.setSilent(true);
                lightning1.setCosmetic(true);
                lightning1.tick();
                lightning1.tick();
                lightning1.tick();
                SeethingLightning lightning2 = new SeethingLightning(aureate.SEETHING_LIGHTNING,attacker.getWorld());
                lightning2.setSilent(true);
                lightning2.setCosmetic(true);
                lightning2.tick();
                lightning2.tick();
                lightning2.tick();
                SeethingLightning lightning3 = new SeethingLightning(aureate.SEETHING_LIGHTNING,attacker.getWorld());
                lightning3.setSilent(true);
                lightning3.setCosmetic(true);
                lightning3.tick();
                lightning3.tick();
                lightning3.tick();
                SeethingLightning lightning4 = new SeethingLightning(aureate.SEETHING_LIGHTNING,attacker.getWorld());
                lightning4.setSilent(true);
                lightning4.setCosmetic(true);
                lightning4.tick();
                lightning4.tick();
                lightning4.tick();

                if(!FabricLoader.getInstance().isModLoaded("bettercombat") && !(player.getOffHandStack().getItem() instanceof Gauntlet)) {
                    player.getItemCooldownManager().set(this, (int) (20F / (float) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED)));
                }                if (attacker.hasStatusEffect(StatusEffectsModded.ETHEREAL)) {
                    attacker.setAbsorptionAmount(attacker.getAbsorptionAmount() + 1);
                }
                List list = target.getWorld().getEntitiesByType(TypeFilter.instanceOf(LivingEntity.class),target.getBoundingBox().expand(6),EntityPredicates.VALID_LIVING_ENTITY);
                if(list.contains(player)) {
                    list.remove(player);
                }
                if(list.isEmpty()){
                    target.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.SEETHING,12, (int) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)*2,false,false));

                        lightning2.setPos(target.getPos().getX(), target.getY(), target.getZ());
                        (target.getWorld()).spawnEntity(lightning2);
                    return super.postHit(stack, target, attacker);
                }
                LivingEntity closest1 = target.getWorld().getClosestEntity(list,TargetPredicate.DEFAULT,target,target.getX(),target.getY(),target.getZ());
                list.remove(closest1);
                if(closest1 == null){
                    target.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.SEETHING,12, (int) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)*2,false,false));

                        lightning2.setPos(target.getPos().getX(), target.getY(), target.getZ());
                        (attacker.getWorld()).spawnEntity(lightning2);

                    return super.postHit(stack, target, attacker);
                }
                LivingEntity closest2 = target.getWorld().getClosestEntity(list,TargetPredicate.DEFAULT,closest1,closest1.getX(),closest1.getY(),closest1.getZ());
                if(closest2 == null){
                    target.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.SEETHING,12, (int) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE),false,false));
                    closest1.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.SEETHING,12, (int) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE),false,false));

                        lightning1.setPos(closest1.getPos().getX(),closest1.getY(),closest1.getZ());
                        (attacker.getWorld()).spawnEntity(lightning1);
                        lightning2.setPos(target.getPos().getX(), target.getY(), target.getZ());
                        (attacker.getWorld()).spawnEntity(lightning2);
                    return super.postHit(stack, target, attacker);

                }
                list.remove(closest2);
                LivingEntity closest3 = target.getWorld().getClosestEntity(list,TargetPredicate.DEFAULT,closest2,closest2.getX(),closest2.getY(),closest2.getZ());
                if(closest3 == null){
                    target.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.SEETHING,12, (int) ((int) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)/1.5),false,false));
                    closest1.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.SEETHING,12, (int) ((int) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)/1.5),false,false));
                    closest2.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.SEETHING,12, (int) ((int) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)/1.5),false,false));

                        lightning2.setPos(closest1.getPos().getX(), closest1.getY(), closest1.getZ());
                        (attacker.getWorld()).spawnEntity(lightning2);
                        lightning1.setPos(closest2.getPos().getX(), closest2.getY(), closest2.getZ());
                        (attacker.getWorld()).spawnEntity(lightning1);
                        lightning3.setPos(target.getPos().getX(), target.getY(), target.getZ());
                        (attacker.getWorld()).spawnEntity(lightning3);

                    return super.postHit(stack, target, attacker);

                }
                else {
                    target.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.SEETHING, 12, (int) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) / 2,false,false));
                    closest1.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.SEETHING, 12, (int) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) / 2,false,false));
                    closest2.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.SEETHING, 12, (int) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) / 2,false,false));
                    closest3.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.SEETHING, 12, (int) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) / 2,false,false));

                        lightning4.setPos(closest3.getPos().getX(), closest3.getY(), closest3.getZ());
                        (attacker.getWorld()).spawnEntity(lightning4);
                        lightning2.setPos(closest1.getPos().getX(), closest1.getY(), closest1.getZ());
                        (attacker.getWorld()).spawnEntity(lightning2);
                        lightning1.setPos(closest2.getPos().getX(), closest2.getY(), closest2.getZ());
                        ( attacker.getWorld()).spawnEntity(lightning1);
                        lightning3.setPos(target.getPos().getX(), target.getY(), target.getZ());
                        (attacker.getWorld()).spawnEntity(lightning3);
                    return super.postHit(stack, target, attacker);
                }

            }

        }
        return super.postHit(stack, target, attacker);
    }
}
