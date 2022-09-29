package net.cleannrooster.aureate.items;

import net.cleannrooster.aureate.StatusEffectsModded;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AureateArmor extends ArmorItem {
    public AureateArmor(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
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
            if(!living.hasStatusEffect(StatusEffectsModded.ETHEREAL) && amount > 0) {
                living.addStatusEffect(new StatusEffectInstance(StatusEffectsModded.ETHEREAL, 20 * 20, 0,false, false));
            }
        }
    }
}
