package net.cleannrooster.aureate.items;

import net.cleannrooster.aureate.StatusEffectsModded;
import net.cleannrooster.aureate.aureate;
import net.cleannrooster.aureate.entities.BlackWolf;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class PrimalAxe extends AxeItem {
    public PrimalAxe(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player) {
            if (!player.getItemCooldownManager().isCoolingDown(this)) {

                if(!FabricLoader.getInstance().isModLoaded("bettercombat") && !(player.getOffHandStack().getItem() instanceof Gauntlet)) {
                    player.getItemCooldownManager().set(this, (int) (20F / (float) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED)));
                }                List<BlackWolf> entityList = player.getWorld().getEntitiesByType(TypeFilter.instanceOf(BlackWolf.class),player.getBoundingBox().expand(64), EntityPredicates.VALID_LIVING_ENTITY);
                entityList.removeIf(wolf -> wolf.getOwner() != attacker);
                if(entityList.isEmpty()){
                    BlackWolf blackWolf = new BlackWolf(aureate.BLACKWOLF,player.getWorld());

                    blackWolf.setTamed(true);
                    blackWolf.setOwner(player);

                    blackWolf.setPos(player.getX(),player.getY()+2,player.getZ());
                    blackWolf.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.ETHEREALWOLF, Math.max(80,(int) (160/attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED))),0, false, false));
                    player.getWorld().spawnEntity(blackWolf);
                    blackWolf.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.ETHEREALWOLF,Math.max(80,(int) (160/attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED))),0, false, false));
                    blackWolf.setAngryAt(target.getUuid());
                }
                else {
                    BlackWolf blackWolf = new BlackWolf(aureate.BLACKWOLF,player.getWorld());

                    blackWolf.setTamed(true);
                    blackWolf.setOwner(player);

                    blackWolf.setPos(player.getX(),player.getY()+2,player.getZ());
                    blackWolf.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.ETHEREALWOLF,Math.max(80,(int) (160/attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED))),0, false, false));
                    player.getWorld().spawnEntity(blackWolf);
                    blackWolf.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.ETHEREALWOLF,Math.max(80,(int) (160/attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED))),0, false, false));
                    blackWolf.setAngryAt(target.getUuid());
                    for(BlackWolf wolf : entityList){
                        wolf.setAngryAt(target.getUuid());
                        wolf.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH,(int) (80F/(float)attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED)), (int) (attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)/3F)-1));
                    }
                }
            }
        }
        return super.postHit(stack, target, attacker);
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return ActionResult.PASS;
    }
}
