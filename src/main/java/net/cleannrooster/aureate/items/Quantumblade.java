package net.cleannrooster.aureate.items;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.cleannrooster.aureate.StatusEffectsModded;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.UUID;

public class Quantumblade extends SwordItem {
    public Quantumblade(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        super.inventoryTick(stack, world, entity, slot, selected);
        if(entity instanceof PlayerEntity player) {
            if (player.getMainHandStack().getItem() == this || player.getOffHandStack().getItem() == this) {
            }
        }
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player) {
            if (!player.getItemCooldownManager().isCoolingDown(this)) {
                if(attacker.hasStatusEffect(StatusEffectsModded.ETHEREAL)){
                    attacker.setAbsorptionAmount(attacker.getAbsorptionAmount() + 2);
                }
                if(!FabricLoader.getInstance().isModLoaded("bettercombat") && !(player.getOffHandStack().getItem() instanceof Gauntlet)) {
                    player.getItemCooldownManager().set(this, (int) (20F / (float) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED)));
                }                player.getWorld().playSoundFromEntity(null,player,SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS,1,1);
                double f1 = -player.getPos().subtract(target.getPos()).normalize().getX();
                double f2 = -player.getPos().subtract(target.getPos()).normalize().getY();
                double f3 = -player.getPos().subtract(target.getPos()).normalize().getZ();

                player.teleport(target.getX()+f1+0.5*target.getBoundingBox().getXLength()*f1, target.getY(), target.getZ()+0.5*target.getBoundingBox().getZLength()*f3+f3);
                player.getWorld().playSoundFromEntity(null,player,SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS,1,1);
            }
        }
        return super.postHit(stack, target, attacker);
    }
}
