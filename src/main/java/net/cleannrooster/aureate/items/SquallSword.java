package net.cleannrooster.aureate.items;

import net.cleannrooster.aureate.StatusEffectsModded;
import net.cleannrooster.aureate.entities.GaleswordProjectile;
import net.cleannrooster.aureate.entities.GauntletProjectile;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class SquallSword extends SwordItem {
    public SquallSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
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
                GaleswordProjectile proj = new GaleswordProjectile(attacker.getWorld(),attacker, true, target);
                proj.setVelocity(attacker, attacker.getPitch(), attacker.getYaw(), 0.0F, 0.5F, 0F);
                ItemStack stack2 = new ItemStack(Items.SNOWBALL);

                proj.setItem(stack2);
                proj.setNoGravity(true);
                attacker.getWorld().spawnEntity(proj);

            }
        }
        return super.postHit(stack, target, attacker);
    }
}
