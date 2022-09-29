package net.cleannrooster.aureate.items;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.cleannrooster.aureate.StatusEffectsModded;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

import java.util.List;

public class AureateSword extends SwordItem {
    public AureateSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player) {
            if (!player.getItemCooldownManager().isCoolingDown(this)) {

                if(!FabricLoader.getInstance().isModLoaded("bettercombat") && !(player.getOffHandStack().getItem() instanceof Gauntlet)) {
                    player.getItemCooldownManager().set(this, (int) (20F / (float) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED)));
                }                if (attacker.hasStatusEffect(StatusEffectsModded.ETHEREAL)) {
                    attacker.setAbsorptionAmount(attacker.getAbsorptionAmount() + 2);
                }
                List<Entity> entities = target.world.getOtherEntities(player, target.getBoundingBox().expand(3));
                for(Entity entity : entities){
                    if(entity instanceof LivingEntity living){
                        if(living.hasStatusEffect(StatusEffects.GLOWING) && living != target){
                            living.damage(DamageSource.player(player), (float) player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
                        }
                        living.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING,(int) (80F/(float)attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED)),0, false, false));
                    }
                }
            }
        }
        return super.postHit(stack, target, attacker);
    }

}
